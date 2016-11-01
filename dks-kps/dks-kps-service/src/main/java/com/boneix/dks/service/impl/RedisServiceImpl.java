package com.boneix.dks.service.impl;

import com.boneix.base.cache.client.CacheClient;
import com.boneix.base.util.JsonUtils;
import com.boneix.base.util.StringUtils;
import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;
import com.boneix.dks.property.SystemParamInit;
import com.boneix.dks.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
@Service
public class RedisServiceImpl implements RedisService {


    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private CacheClient cacheClient;

    @Override
    public void produceKey(SystemsInfoVo systemsInfoVo) {
        try {
            long currentValue = systemsInfoVo.getCurrentValue();
            for (int i = 0; i < SystemParamInit.getKeyProduceRang(); i++) {
                cacheClient.rpush(String.valueOf(systemsInfoVo.getId()), String.valueOf(currentValue + i));
            }
        } catch (Exception e) {
            LOG.error("生成Key失败，Redis入栈异常", e);
        }
    }

    @Override
    public long consumeKey(SysInfo sysInfo) {
        long currentKey =0;
        try {
            String tmp=cacheClient.lpop(String.valueOf(sysInfo.getId()));
            if(StringUtils.isNotEmpty(tmp)){
                currentKey=Long.valueOf(tmp);
            }else{
                LOG.info("Redis存储的Key未初始化或已消费完");
            }
        } catch (Exception e) {
            LOG.error("生成Key失败，Redis入栈异常", e);
        }
        return currentKey;
    }
}
