package com.boneix.dks.controller;

import com.boneix.base.annotation.Json;
import com.boneix.base.domain.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/opt")
public class InitOperateController {

    private static final Logger LOG = LoggerFactory.getLogger(InitOperateController.class);

//    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
//    @ResponseJson
//    public ResponseVo selectList(@Json Map<String, Object> request){
//
//    }

}
