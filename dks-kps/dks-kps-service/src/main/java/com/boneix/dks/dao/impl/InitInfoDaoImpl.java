package com.boneix.dks.dao.impl;

import com.boneix.base.dao.impl.BaseDaoImpl;
import com.boneix.dks.dao.InitInfoDao;
import com.boneix.dks.domain.InitInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
@Repository
public class InitInfoDaoImpl extends BaseDaoImpl<InitInfo> implements InitInfoDao {

    @Override
    public long queryCurrentValue(long sysId) {
        return sqlSessionTemplate.selectOne(getSqlName("queryCurrentValue"), sysId);
    }

    @Override
    public int updateCurrentValue(long sysId, long newValue) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("sysId", sysId);
        reqMap.put("newValue", newValue);
        return sqlSessionTemplate.update(getSqlName("updateCurrentValue"), reqMap);
    }

    @Override
    public int deleteCurrentValue(long sysId) {
        return sqlSessionTemplate.update(getSqlName("deleteCurrentValue"), sysId);
    }


    @Override
    public int querySystemCount(long sysId) {
        return sqlSessionTemplate.selectOne(getSqlName("querySystemCount"), sysId);
    }
}
