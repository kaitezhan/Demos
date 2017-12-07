package com.boneix.demo.consumer.service.impl;

import com.boneix.demo.consumer.result.base.ResultEntity;
import com.boneix.demo.consumer.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rzhang on 2017/11/20.
 */
@Service
public class RestServiceImpl implements RestService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResultEntity sayHi() {
        return restTemplate.getForObject("http://CLOUD-CLIENT-01/hello/hello",ResultEntity.class);
    }
}
