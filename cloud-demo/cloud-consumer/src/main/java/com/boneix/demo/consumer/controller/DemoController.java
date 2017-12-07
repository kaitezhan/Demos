package com.boneix.demo.consumer.controller;

import com.boneix.demo.consumer.result.base.ResultEntity;
import com.boneix.demo.consumer.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rzhang on 2017/11/20.
 */
@RestController
public class DemoController {

    @Autowired
    private RestService restService;

    @RequestMapping("/hello")
    public ResultEntity home() {
        return restService.sayHi();
    }
}
