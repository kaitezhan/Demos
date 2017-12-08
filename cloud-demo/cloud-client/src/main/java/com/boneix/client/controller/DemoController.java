package com.boneix.client.controller;

import com.boneix.client.annotation.ApiCode;
import com.boneix.client.annotation.ApiScanner;
import com.boneix.client.entity.ApiDetailVO;
import com.boneix.client.result.Results;
import com.boneix.client.result.base.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by rzhang on 2017/11/20.
 */
@ApiScanner
@Slf4j
@RestController
@RequestMapping("/hello")
public class DemoController {


    @ApiCode(value = "demo.hello", skip = true)
    @RequestMapping("/hello")
    public ResultEntity home(HttpServletRequest request) {
        String ipAddress = getRemoteHost(request);
        log.info("{} 请求访问服务器 ", ipAddress);
        return Results.newEmptyResultEntity();
    }

    @ApiCode(value = "demo.hello3", skip = true)
    @PostMapping("/hello3/{type}")
    public ResultEntity home3(HttpServletRequest request, @RequestBody ApiDetailVO vo,@PathVariable String type) {
        log.info("type is {} ", type);
        return Results.newSingleResultEntity(vo);
    }

    @ApiCode(value = "demo.hello2")
    @RequestMapping("/hello2")
    public ResultEntity home2(HttpServletRequest request) {
        String ipAddress = getRemoteHost(request);
        log.debug("{} 请求访问服务器 ", ipAddress);
        return Results.newEmptyResultEntity();
    }

    /**
     * 获取远程请求的ip
     *
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取ip地址失败，{}", e);
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
