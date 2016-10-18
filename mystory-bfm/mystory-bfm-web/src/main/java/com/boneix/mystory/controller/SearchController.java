package com.boneix.mystory.controller;

import com.boneix.base.domain.ResponseVo;
import com.boneix.mystory.service.ActionIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * Created by zhangrong5 on 2016/9/26.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/search")
public class SearchController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @Resource
    private ActionIndexService actionIndexService;

    @RequestMapping(value = "/ActionDocsById")
    @ResponseBody
    public ResponseVo searchActionDocsById(@RequestParam(value = "actionId", required = true) int actionId) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setData(actionIndexService.queryActionDocumentByActionId(actionId));
        return responseVo;
    }
}
