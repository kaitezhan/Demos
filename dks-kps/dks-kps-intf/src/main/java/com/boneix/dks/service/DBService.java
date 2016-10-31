package com.boneix.dks.service;

import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public interface DBService {

    long insertInitInfo(long sysId, long newValue);

    long deleteInitInfo(long sysId);

    long updateInitInfo(long sysId, long newValue);

    long queryCurrentValue(long sysId);

    long insertSysInfo(SysInfo sysInfo);

    long deleteSysInfo(long sysId);

    long updateSysInfo(long sysId, String sysName);

    List<SystemsInfoVo> selectSystemsInfo();
}
