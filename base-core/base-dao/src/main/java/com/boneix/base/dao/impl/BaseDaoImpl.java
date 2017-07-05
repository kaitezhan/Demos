package com.boneix.base.dao.impl;

import com.boneix.base.dao.BaseDao;
import com.boneix.base.dao.Identifiable;
import com.boneix.base.dao.constants.SqlId;
import com.boneix.base.dao.exception.DaoException;
import com.boneix.base.dao.util.BeanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 *
 * @author hu_guodong
 * @version [1.0, 2014年12月8日]
 */
public abstract class BaseDaoImpl<T extends Identifiable> implements BaseDao<T> {
    public static final String SQLNAME_SEPARATOR = ".";
    @Resource
    protected SqlSession sqlSessionTemplate;
    /**
     * @fields sqlNamespace SqlMapping命名空间
     */
    private String sqlNamespace = getDefaultSqlNamespace();

    /**
     * 获取泛型类型的实体对象类全名
     *
     * @return defaultSqlNamespace
     */
    protected String getDefaultSqlNamespace() {
        Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
        return genericClass == null ? null : genericClass.getName();
    }

    /**
     * 获取SqlMapping命名空间
     *
     * @return SqlMapping命名空间
     */
    public String getSqlNamespace() {
        return sqlNamespace;
    }

    /**
     * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
     *
     * @param sqlNamespace SqlMapping命名空间
     */
    public void setSqlNamespace(String sqlNamespace) {
        this.sqlNamespace = sqlNamespace;
    }

    /**
     * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
     *
     * @param sqlName SqlMapping名
     * @return 组合了SqlMapping命名空间后的完整SqlMapping名
     */
    protected String getSqlName(String sqlName) {
        return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
    }

    /**
     * 获取分页查询参数
     *
     * @param query    查询对象
     * @param pageable 分页对象
     * @return Map 查询参数
     */
    protected Map<String, Object> getParams(T query, Pageable pageable) {
        Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
        if (pageable != null && pageable.getSort() != null) {
            String sorting = pageable.getSort().toString();
            params.put("sorting", sorting.replace(":", ""));
        }
        return params;
    }

    /**
     * 设置分页
     *
     * @param pageable 分页信息
     * @return SQL分页参数对象
     */
    protected RowBounds getRowBounds(Pageable pageable) {
        RowBounds bounds = RowBounds.DEFAULT;
        if (null != pageable) {
            bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
        }
        return bounds;
    }

    /*
     * (non-Javadoc)
     * @see com.viathink.core.dao.BaseDao#selectOne(java.io.Serializable)
     */
    @Override
    public <V extends T> V selectOne(T query) {
        Assert.notNull(query);
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT), params);
        } catch (Exception e) {
            throw new DaoException(String.format("selectOne error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <V extends T> V selectById(long id) {
        Assert.notNull(id);
        try {
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_BY_ID), id);
        } catch (Exception e) {
            throw new DaoException(String.format("selectById error! statement: %s", getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
        }
    }

    @Override
    public <K, V extends T> Map<K, V> selectMap(T query, String mapKey) {
        Assert.notNull(mapKey, "[mapKey] - must not be null!");
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), params, mapKey);
        } catch (Exception e) {
            throw new DaoException(String.format("selectMap error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable) {
        try {
            return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable), mapKey);
        } catch (Exception e) {
            throw new DaoException(String.format("selectMap error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <V extends T> List<V> selectList(T query) {
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), params);
        } catch (Exception e) {
            throw new DaoException(String.format("selectList error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <V extends T> List<V> selectList(T query, Pageable pageable) {
        try {
            return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
        } catch (Exception e) {
            throw new DaoException(String.format("selectList error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <V extends T> Page<V> selectPageList(T query, Pageable pageable) {
        try {
            List<V> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
            return new PageImpl<>(contentList, pageable, this.selectCount(query));
        } catch (Exception e) {
            throw new DaoException(String.format("selectPageList error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public <V extends T> List<V> selectAll() {
        try {
            return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT));
        } catch (Exception e) {
            throw new DaoException(String.format("selectAll error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }
    }

    @Override
    public long selectCount() {
        try {
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT));
        } catch (Exception e) {
            throw new DaoException(String.format("selectCount error! statement: %s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
        }
    }

    @Override
    public long selectCount(T query) {
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
        } catch (Exception e) {
            throw new DaoException(String.format("selectCount error! statement: %s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
        }
    }

    @Override
    public long insert(T entity) {
        Assert.notNull(entity);
        try {
            return sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), entity);
        } catch (Exception e) {
            throw new DaoException(String.format("insert error! statement: %s", getSqlName(SqlId.SQL_INSERT)), e);
        }
    }

    @Override
    public void insertInBatch(List<T> entityList) {
        if (entityList == null || entityList.isEmpty())
            return;
        for (T entity : entityList) {
            this.insert(entity);
        }
    }

    @Override
    public long delete(T query) {
        Assert.notNull(query);
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE), params);
        } catch (Exception e) {
            throw new DaoException(String.format("delete error! statement: %s", getSqlName(SqlId.SQL_DELETE)), e);
        }
    }

    @Override
    public long deleteById(long id) {
        Assert.notNull(id);
        try {
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE_BY_ID), id);
        } catch (Exception e) {
            throw new DaoException(String.format("deleteById error! statement: %s", getSqlName(SqlId.SQL_DELETE_BY_ID)), e);
        }
    }

    @Override
    public long deleteInLogical(T query) {
        Assert.notNull(query);
        try {
            Map<String, Object> params = BeanUtils.toMap(query);
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_DELETE_IN_LOGICAL), params);
        } catch (Exception e) {
            throw new DaoException(String.format("deleteInLogical error! statement: %s", getSqlName(SqlId.SQL_DELETE_IN_LOGICAL)), e);
        }
    }

    @Override
    public long deleteByIdInLogical(long id) {
        Assert.notNull(id);
        try {
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_DELETE_BY_ID_IN_LOGICAL), id);
        } catch (Exception e) {
            throw new DaoException(String.format("deleteById error! statement: %s", getSqlName(SqlId.SQL_DELETE_BY_ID_IN_LOGICAL)), e);
        }
    }

    @Override
    public long deleteAll() {
        try {
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE));
        } catch (Exception e) {
            throw new DaoException(String.format("deleteAll error! statement: %s", getSqlName(SqlId.SQL_DELETE)), e);
        }
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Long> idList) {
        if (idList == null || idList.isEmpty())
            return;
        for (Long id : idList) {
            this.deleteById(id);
        }
    }

    @Override
    public long updateById(T entity) {
        Assert.notNull(entity);
        try {
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);
        } catch (Exception e) {
            throw new DaoException(String.format("updateById error! statement: %s", getSqlName(SqlId.SQL_UPDATE_BY_ID)), e);
        }
    }

    @Override
    @Transactional
    public long updateByIdSelective(T entity) {
        Assert.notNull(entity);
        try {
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE), entity);
        } catch (Exception e) {
            throw new DaoException(String.format("updateByIdSelective error! statement: %s", getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE)), e);
        }
    }

    @Override
    @Transactional
    public void updateInBatch(List<T> entityList) {
        if (entityList == null || entityList.isEmpty())
            return;
        for (T entity : entityList) {
            this.updateByIdSelective(entity);
        }
    }

    @Override
    public boolean checkName(T query) {
        Assert.notNull(query);

        try {
            Map<String, Object> params = BeanUtils.toMap(query);

            List<T> list = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_CHECK_NAME), params);

            return list != null && !list.isEmpty();
        } catch (Exception e) {
            throw new DaoException(String.format("selectOne error! statement: %s", getSqlName(SqlId.SQL_SELECT)), e);
        }

    }
}
