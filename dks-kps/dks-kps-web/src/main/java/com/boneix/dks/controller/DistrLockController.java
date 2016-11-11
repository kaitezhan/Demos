package com.boneix.dks.controller;

import com.boneix.base.annotation.Json;
import com.boneix.base.domain.CommonError;
import com.boneix.base.domain.ResponseVo;
import com.boneix.dks.domain.DistrKeyInfo;
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
 * Created by zhangrong5 on 2016/11/11.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/lock")
public class DistrLockController {

    private static final Logger LOG = LoggerFactory.getLogger(DistrLockController.class);

    @Resource
    private RedisService redisService;


    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo occupyLock(HttpServletRequest request, @Json DistrKeyInfo distrKeyInfo) {
       // LOG.info("occupyLock.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo.setData(redisService.tryOccupyLock(distrKeyInfo));
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("占用Key异常! {}", e);
        }
        return responseVo;
    }

    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo releaseLock(HttpServletRequest request, @Json DistrKeyInfo distrKeyInfo) {
        LOG.info("releaseLock.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo.setData(redisService.tryReleaseLock(distrKeyInfo));
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("释放Key异常! {}", e);
        }
        return responseVo;
    }
}
