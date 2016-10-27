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
    public int insertByName(String sysName) {
        return sqlSessionTemplate.insert(getSqlName("insertByName"), sysName);
    }

    @Override
    public long querySystemCount(int sysId) {
        return sqlSessionTemplate.selectOne(getSqlName("querySystemCount"), sysId);
    }
}
