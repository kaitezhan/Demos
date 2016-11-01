package com.boneix.dks.dao.impl;

import com.boneix.base.dao.impl.BaseDaoImpl;
import com.boneix.dks.dao.SysInfoDao;
import com.boneix.dks.domain.SysInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
@Repository
public class SysInfoDaoImpl extends BaseDaoImpl<SysInfo> implements SysInfoDao {
    @Override
    public int insertByName(SysInfo sysInfo) {
        return sqlSessionTemplate.insert(getSqlName("insertByName"), sysInfo);
    }

    @Override
    public long querySystemCount(long sysId) {
        return sqlSessionTemplate.selectOne(getSqlName("querySystemCount"), sysId);
    }

    @Override
    public int existSystem(SysInfo sysInfo) {
        return sqlSessionTemplate.selectOne(getSqlName("existSystem"), sysInfo);
    }
}
