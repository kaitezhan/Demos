package com.boneix.dks.service;

import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
public interface RedisService {
    void produceKey(SystemsInfoVo systemsInfoVo);

    long consumeKey(SysInfo sysInfo);
}
