package com.boneix.dks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangrong5 on 2016/10/31.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/view")
public class ViewController {
    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(value = "/rest", method = RequestMethod.GET)
    public ModelAndView restPage(HttpServletRequest request) {
        LOG.info("restPage.userIp {}", request.getLocalAddr());
        return new ModelAndView("urlRest");
    }

}
