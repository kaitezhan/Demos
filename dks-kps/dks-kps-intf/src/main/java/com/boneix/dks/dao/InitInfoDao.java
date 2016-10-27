package com.boneix.dks.dao;

import com.boneix.base.dao.BaseDao;
import com.boneix.dks.domain.InitInfo;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public interface InitInfoDao extends BaseDao<InitInfo> {

    long queryCurrentValue(int sysId);

    int updateCurrentValue(int sysId, long newValue);

    int deleteCurrentValue(int sysId);

    int querySystemCount(int sysId);
}
