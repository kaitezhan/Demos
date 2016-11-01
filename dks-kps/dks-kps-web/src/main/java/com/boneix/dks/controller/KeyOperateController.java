package com.boneix.dks.controller;

import com.boneix.base.annotation.Json;
import com.boneix.base.domain.CommonError;
import com.boneix.base.domain.ResponseVo;
import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;
import com.boneix.dks.property.SystemParamInit;
import com.boneix.dks.service.DBService;
import com.boneix.dks.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/key")
public class KeyOperateController {

    private static final Logger LOG = LoggerFactory.getLogger(KeyOperateController.class);

    @Resource
    private DBService dbService;

    @Resource
    private RedisService redisService;

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo produceKey(HttpServletRequest request, @Json SystemsInfoVo systemsInfoVo) {
        LOG.info("produceKey.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            if (confirmSystem(systemsInfoVo)) {
                dbService.updateInitInfo(systemsInfoVo.getId(), (systemsInfoVo.getCurrentValue() + SystemParamInit.keyProduceRang));
                redisService.produceKey(systemsInfoVo);
            } else {
                responseVo.setMsg("请重新验证是否需要生成Key");
            }
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("初始化Key异常! {}", e);
        }
        return responseVo;
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo consumeKey(HttpServletRequest request, @Json SysInfo sysInfo) {
        LOG.info("consumeKey.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            if (existSystem(sysInfo)) {
                long key = redisService.consumeKey(sysInfo);
                SystemsInfoVo systemsInfoVo = SystemsInfoVo.superClone(sysInfo);
                systemsInfoVo.setUsedValue(key);
                if (needProduceKey(systemsInfoVo, key)) {
                    dbService.updateInitInfo(systemsInfoVo.getId(), (systemsInfoVo.getCurrentValue() + SystemParamInit.keyProduceRang));
                    redisService.produceKey(systemsInfoVo);
                    //生成完毕后重新获取值
                    if(key==0){
                        key = redisService.consumeKey(sysInfo);
                    }
                }
                responseVo.setData(key);
            } else {
                CommonError.setErrorCode(responseVo, CommonError.QUERY_ERROR);
                responseVo.setMsg("无权限获取Key");
            }
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("申请Key异常! {}", e);
        }
        return responseVo;
    }

    private boolean needProduceKey(SystemsInfoVo systemsInfoVo, long key) {
        long currentValue = dbService.queryCurrentValue(systemsInfoVo.getId());
        systemsInfoVo.setCurrentValue(currentValue);
        if (key == 0) {
            return true;
        } else {
            if (key + SystemParamInit.keyWarningRang > currentValue) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean existSystem(SysInfo sysInfo) {
        return dbService.existSystem(sysInfo);
    }


    private boolean confirmSystem(SystemsInfoVo systemsInfoVo) {
        long currentValue = systemsInfoVo.getCurrentValue();
        long usedValue = systemsInfoVo.getUsedValue();
        if (usedValue == 0) {
            return true;
        } else {
            if ((currentValue - SystemParamInit.keyWarningRang) > usedValue) {
                return false;
            } else {
                return dbService.confirmSystem(systemsInfoVo);
            }
        }
    }
}
