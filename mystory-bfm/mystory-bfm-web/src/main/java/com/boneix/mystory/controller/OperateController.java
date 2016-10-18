package com.boneix.mystory.controller;

import com.boneix.base.domain.ResponseVo;
import com.boneix.base.util.JsonUtils;
import com.boneix.mystory.domain.ActionDocument;
import com.boneix.mystory.jms.producer.ActionProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangrong5 on 2016/10/13.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/operate")
public class OperateController {
    private static final Logger LOG = LoggerFactory.getLogger(OperateController.class);

    @Resource
    private ActionProducer actionProducer;

    @RequestMapping(value = "/insertDocs")
    @ResponseBody
    public ResponseVo insertDocs() {
        ResponseVo responseVo = new ResponseVo();
        try {
            actionProducer.send(createTmpDoc());
        }catch(Exception e){
            LOG.error("保存数据失败:{}", e);
        }

        return responseVo;
    }

    private ActionDocument createTmpDoc(){
        String json="{'userId':100013,'userName':'鬼马','chapterId':3450,'chapterName':'高中','actionId':455,'actionName':'暑假','parters':[{'parterId':352,'parterName':'王说二'},{'parterId':463,'parterName':'赵点事'}],'createTime':'2015-12-15T11:06:36'}";
        ActionDocument actionDocument= JsonUtils.toBean(json,ActionDocument.class);
        return  actionDocument;
    }


}
