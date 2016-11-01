package com.boneix.dks.dao;

import com.boneix.base.dao.BaseDao;
import com.boneix.dks.domain.InitInfo;
import com.boneix.dks.domain.SystemsInfoVo;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public interface InitInfoDao extends BaseDao<InitInfo> {

    long queryCurrentValue(long sysId);

    int updateCurrentValue(long sysId, long newValue);

    int deleteCurrentValue(long sysId);

    int querySystemCount(long sysId);

    int confirmSystem(SystemsInfoVo systemsInfoVo);
}
