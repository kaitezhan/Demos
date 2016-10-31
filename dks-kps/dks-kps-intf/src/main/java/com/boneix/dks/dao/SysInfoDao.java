package com.boneix.dks.dao;

import com.boneix.base.dao.BaseDao;
import com.boneix.dks.domain.SysInfo;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public interface SysInfoDao extends BaseDao<SysInfo> {
    int insertByName(SysInfo sysInfo);

    long querySystemCount(long sysId);
}
