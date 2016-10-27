package com.boneix.dks.service;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public interface DBService {

    long insertInitInfo(int sysId, long newValue);

    long deleteInitInfo(int sysId);

    long updateInitInfo(int sysId, long newValue);

    long queryCurrentValue(int sysId);

    long insertSysInfo(String sysName);

    long deleteSysInfo(int sysId);

    long updateSysInfo(int sysId, String sysName);
}
