package com.boneix.dks.controller;

import com.boneix.base.annotation.Json;
import com.boneix.base.domain.CommonError;
import com.boneix.base.domain.ResponseVo;
import com.boneix.base.util.JsonUtils;
import com.boneix.base.util.StringUtils;
import com.boneix.dks.domain.SystemsInfoVo;
import com.boneix.dks.service.DBService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/opt")
public class InitOperateController {

    @Resource
    private DBService dbService;

    private static final Logger LOG = LoggerFactory.getLogger(InitOperateController.class);

    @RequestMapping(value = "/systemsQuery", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo systemsQuery(HttpServletRequest request) {
        LOG.info("systemsQuery.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            List<SystemsInfoVo> systemsInfoVos = dbService.selectSystemsInfo();
            if (CollectionUtils.isNotEmpty(systemsInfoVos)) {
                responseVo.setData(systemsInfoVos);
            } else {
                CommonError.setErrorCode(responseVo, CommonError.QUERY_ERROR);
                responseVo.setMsg("查询系统信息数据为空!");
                LOG.info("查询系统信息数据为空!");
            }
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("查询系统信息数据异常! {}", e);
        }
        return responseVo;
    }

    @RequestMapping(value = "/systemAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo systemAdd(HttpServletRequest request, @Json SystemsInfoVo systemsInfoVo) {
        LOG.info("systemAdd.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            boolean res = true;
            boolean paramRes = true;
            if (StringUtils.isNotEmpty(systemsInfoVo.getSystemName())) {
                long sysId = dbService.insertSysInfo(systemsInfoVo);
                if (sysId > 0) {
                    long currentValue = dbService.queryCurrentValue(sysId);
                    if (currentValue < 1) {
                        res = false;
                    }else{
                        responseVo.setData(sysId);
                    }
                } else {
                    res = false;
                }
            } else {
                paramRes = false;
            }
            if(!paramRes){
                CommonError.setErrorCode(responseVo, CommonError.PARAM_ERROR);
                responseVo.setMsg("添加系统信息失败 参数错误!");
                LOG.info("添加系统信息失败  参数错误! {}", JsonUtils.toString(systemsInfoVo));
            }else if (!res) {
                CommonError.setErrorCode(responseVo, CommonError.INSERT_ERROR);
                responseVo.setMsg("添加系统信息失败!");
                LOG.info("添加系统信息失败! {}", JsonUtils.toString(systemsInfoVo));
            }
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("添加系统信息异常! {}", e);
        }
        return responseVo;
    }

}
