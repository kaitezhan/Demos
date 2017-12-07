package com.boneix.client.controller;

import com.boneix.client.entity.ApiDetailVO;
import com.boneix.client.result.Results;
import com.boneix.client.result.base.ResultEntity;
import com.boneix.client.service.InnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by rzhang on 2017/11/20.
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private InnerService innerService;

    @RequestMapping("/scan_api")
    public ResultEntity scanApi() {
        List<ApiDetailVO> apiList = innerService.scanApi();
        return Results.newSingleResultEntity(apiList);
    }
}
