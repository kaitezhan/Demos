package com.boneix.demo.consumer.service.impl;

import com.boneix.demo.consumer.entity.ApiDetailVO;
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
        ApiDetailVO vo=new ApiDetailVO();
        vo.setCode("this is a code");
        vo.setMapping("this is a mapping");
        String type="asda";
        String params="&aaa=1&bbb=2&ccc=3";
        String url="http://CLOUD-CLIENT-01/hello/hello3/"+type+"?"+params;
        return restTemplate.postForObject(url,vo,ResultEntity.class);
    }
}
