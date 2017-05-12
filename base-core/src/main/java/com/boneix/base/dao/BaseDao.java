package com.boneix.base.dao;

import com.boneix.base.domain.Identifiable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao接口
 *
 * @author hu_guodong
 * @version [版本号, 2014年12月8日]
 * @since [产品/模块版本]
 */
public interface BaseDao<T extends Identifiable> {

    /**
     * 查询一个对象，如果返回的结果多于一个对象将会抛出TooManyResultsException
     *
     * @param query 查询对象，不能为null
     * @param <V>   vo对象
     * @return Mapper中映射的对象，继承自 T对象，一般是Vo对象
     */
    <V extends T> V selectOne(T query);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     *
     * @param id  主键，不能为null
     * @param <V> vo对象
     * @return 结果对象，如果未找到返回null
     */
    <V extends T> V selectById(long id);

    /**
     * 查询对象列表
     *
     * @param query 查询参数，如果未null则查询所有，相当于调用方法 {@link com.tuniu.pcs.base.dao.BaseDao }selectAll
     * @param <V>   vo对象
     * @return 结果对象列表
     */
    <V extends T> List<V> selectList(T query);

    /**
     * 查询所有记录列表
     *
     * @param <V> vo对象
     * @return List 结果列表
     */
    <V extends T> List<V> selectAll();

    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>    返回Map的key类型
     * @param <V>    返回Map的Value类型
     * @param query  查询参数,如果未null则查询所有对象
     * @param mapKey 返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @return Map 包含key属性值的Map对象
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey);

    /**
     * <pre>
     * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
     * </pre>
     *
     * @param query    查询参数
     * @param pageable 分页对象
     * @param <V>      vo对象
     * @return List 根据分页对象查询的分页结果列表
     */
    <V extends T> List<V> selectList(T query, Pageable pageable);

    /**
     * <pre>
     * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
     * </pre>
     *
     * @param query    查询参数
     * @param pageable 分页对象
     * @return Page 信息方便前台显示
     */
    <V extends T> Page<V> selectPageList(T query, Pageable pageable);

    /**
     * 根据结果集中的一列作为key，将结果集转换成Map
     *
     * @param <K>      返回Map的key类型
     * @param <V>      返回Map的Value类型
     * @param query    查询参数
     * @param mapKey   返回结果List中‘mapKey’属性值作为Key (The property to use as key for each value in the list.)
     * @param pageable 分页对象
     * @return Map containing key pair data.
     */
    <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable);

    /**
     * 查询总记录数
     *
     * @return long 记录总数
     */
    long selectCount();

    /**
     * 查询记录数
     *
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    long selectCount(T query);

    /**
     * 添加对象
     *
     * @param entity 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    long insert(T entity);

    /**
     * 批量插入，如果为空列表则直接返回
     *
     * @param entityList 需要批量插入的实体对象列表
     */
    void insertInBatch(List<T> entityList);

    /**
     * 删除对象
     *
     * @param query 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    long delete(T query);

    /**
     * 逻辑删除对象，设置对象deleteFlag = 1
     *
     * @param query 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    long deleteInLogical(T query);

    /**
     * 根据Id删除对象
     *
     * @param id 要删除的ID，不能为null
     * @return int 受影响结果数
     */
    long deleteById(long id);

    /**
     * 根据Id逻辑删除对象，设置对象deleteFlag = 1
     *
     * @param id 要删除的ID，不能为null
     * @return int 受影响结果数
     */
    long deleteByIdInLogical(long id);

    /**
     * 删除所有
     *
     * @return int 受影响结果数
     */
    long deleteAll();

    /**
     * 根据id，批量删除记录，如果传入的列表为null或为空列表则直接返回
     *
     * @param idList 批量删除ID列表
     */
    void deleteByIdInBatch(List<Long> idList);

    /**
     * 更新对象，对象必须设置ID
     *
     * @param entity 实体的Id不能为null
     * @return int 受影响结果数
     */
    long updateById(T entity);

    /**
     * 更新对象中已设置的字段，未设置的字段不更新
     *
     * @param entity 要更新的实体对象，不能为null，切ID必须不为null
     * @return int 受影响结果数
     */
    long updateByIdSelective(T entity);

    /**
     * 批量更新，该方法根据实体ID更新已设置的字段，未设置的字段不更新
     *
     * @param entityList 批量更新的实体对象列表
     */
    void updateInBatch(List<T> entityList);

    /**
     * 根据名称查询是否已存在
     *
     * @param entity
     * @return
     */
    boolean checkName(T entity);

}
