package com.boneix.dks.controller;

import com.boneix.base.annotation.Json;
import com.boneix.base.domain.CommonError;
import com.boneix.base.domain.ResponseVo;
import com.boneix.dks.domain.Comment;
import com.boneix.dks.service.CommentService;
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
 * Created by rzhang on 2017/3/9.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/demo")
public class DemoTestController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoTestController.class);

    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addComment(HttpServletRequest request, @Json Comment comment) {
        // LOG.info("occupyLock.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo.setData(commentService.addComment(comment));
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("添加失败! {}", e);
        }
        return responseVo;
    }

    @RequestMapping(value = "/addCommentNoLock", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addCommentNoLock(HttpServletRequest request, @Json Comment comment) {
        // LOG.info("occupyLock.userIp {}", request.getLocalAddr());
        ResponseVo responseVo = new ResponseVo();
        try {
            responseVo.setData(commentService.addCommentWithoutLock(comment));
        } catch (Exception e) {
            CommonError.setErrorCode(responseVo, CommonError.INTER_ERROR);
            LOG.error("添加失败! {}", e);
        }
        return responseVo;
    }
}
