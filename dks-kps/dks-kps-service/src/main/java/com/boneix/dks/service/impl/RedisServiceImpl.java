package com.boneix.dks.service.impl;

import com.boneix.base.cache.client.CacheClient;
import com.boneix.base.util.DateUtils;
import com.boneix.base.util.JsonUtils;
import com.boneix.base.util.StringUtils;
import com.boneix.dks.domain.DistrKeyInfo;
import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;
import com.boneix.dks.property.SystemParamInit;
import com.boneix.dks.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
@Service
public class RedisServiceImpl implements RedisService {


    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);

    private static final int OCCUPY_LOCK_SUCCESS = 1;

    private static final int OCCUPY_LOCK_FAIL = 0;

    private static final int OCCUPY_LOCK_EXCEPTION = -1;

    private static final int OCCUPY_LOCK_PARAM_ERROR = -2;

    @Resource(name = "cacheClient")
    private CacheClient cacheClient;

    @Resource(name = "distrKeyClient")
    private CacheClient keyClient;

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
        long currentKey = 0;
        try {
            String tmp = cacheClient.lpop(String.valueOf(sysInfo.getId()));
            if (StringUtils.isNotEmpty(tmp)) {
                currentKey = Long.valueOf(tmp);
            } else {
                LOG.info("生成Key失败，key未初始化或已消费完");
            }
        } catch (Exception e) {
            LOG.error("生成Key失败，Redis入栈异常", e);
        }
        return currentKey;
    }

    @Override
    public long tryOccupyLock(DistrKeyInfo distrKeyInfo) {
        long status = OCCUPY_LOCK_FAIL;
        distrKeyInfo.setCreateTime(new Date());
        try {
            //检查参数
            if (!isConformParamsNorms(distrKeyInfo)) {
                return OCCUPY_LOCK_PARAM_ERROR;
            }
            //如果存在
            String value = keyClient.get(distrKeyInfo.getKey());
            if (StringUtils.isNotEmpty(value)) {
                DistrKeyInfo tmpInfo = JsonUtils.toBean(value, DistrKeyInfo.class);
                Date tmpDate = DateUtils.addSeconds(tmpInfo.getCreateTime(), tmpInfo.getInvalidTime());
                //如果超时redis 未自动删除
                if (distrKeyInfo.getCreateTime().after(tmpDate)) {
                    synchronized (this) {
                        // 两次判断
                        String tmpValue = keyClient.get(distrKeyInfo.getKey());
                        if (StringUtils.isNotEmpty(tmpValue)) {
                            DistrKeyInfo tmp2Info = JsonUtils.toBean(tmpValue, DistrKeyInfo.class);
                            Date tmp2Date = DateUtils.addSeconds(tmp2Info.getCreateTime(), tmp2Info.getInvalidTime());
                            if (distrKeyInfo.getCreateTime().after(tmp2Date)) {
                                status = doOccupyLock(distrKeyInfo);
                            }
                        } else {
                            //如果不存在
                            status = doOccupyLock(distrKeyInfo);
                        }
                    }
                } else if (isSameKeyInfo(tmpInfo, distrKeyInfo)) {
                    //如果未超时 是同一个系统 同一个机器
                    status = OCCUPY_LOCK_SUCCESS;
                }
            } else {
                //如果不存在
                synchronized (this) {
                    // 两次判断
                    if (!keyClient.exists(distrKeyInfo.getKey())) {
                        status = doOccupyLock(distrKeyInfo);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("占用Key失败，Redis异常", e);
            status = OCCUPY_LOCK_EXCEPTION;
        }

        return status;
    }

    @Override
    public long tryReleaseLock(DistrKeyInfo distrKeyInfo) {
        long status = OCCUPY_LOCK_FAIL;
        try {
            //检查参数
            if (!isConformParamsNorms(distrKeyInfo)) {
                return OCCUPY_LOCK_PARAM_ERROR;
            }
            //如果存在
            if (keyClient.exists(distrKeyInfo.getKey())) {
                String tmpValue = keyClient.get(distrKeyInfo.getKey());
                DistrKeyInfo tmpInfo = JsonUtils.toBean(tmpValue, DistrKeyInfo.class);
                if (isSameKeyInfo(tmpInfo, distrKeyInfo)) {
                    if (keyClient.del(distrKeyInfo.getKey()) > 0) {
                        status = OCCUPY_LOCK_SUCCESS;
                    }
                } else {
                    LOG.info("释放Key失败，认证码不一致！");
                }
            } else {
                LOG.info("释放Key失败，key不存在！");
            }
        } catch (Exception e) {
            LOG.error("释放Key失败，Redis异常", e);
            status = OCCUPY_LOCK_EXCEPTION;
        }
        return status;
    }

    private boolean isConformParamsNorms(DistrKeyInfo distrKeyInfo) {
        return (StringUtils.isNotEmpty(distrKeyInfo.getKey()))
                && (distrKeyInfo.getInvalidTime() > 0)
                && (null != distrKeyInfo.getSysInfo())
                && (StringUtils.isNotEmpty(distrKeyInfo.getSysInfo().getAuthorityCode()))
                && (distrKeyInfo.getSysInfo().getId() > 0);
    }


    private long doOccupyLock(DistrKeyInfo distrKeyInfo) {
        keyClient.setex(distrKeyInfo.getKey(), distrKeyInfo.getInvalidTime(), JsonUtils.toString(distrKeyInfo));
        LOG.info("占用key成功，{}", JsonUtils.toString(distrKeyInfo));
        return OCCUPY_LOCK_SUCCESS;
    }

    private boolean isSameKeyInfo(DistrKeyInfo tmpInfo, DistrKeyInfo distrKeyInfo) {
        boolean flag = false;
        if (tmpInfo.getSysInfo().getAuthorityCode().equals(distrKeyInfo.getSysInfo().getAuthorityCode())) {
            flag = true;
        }
        return flag;
    }
}
