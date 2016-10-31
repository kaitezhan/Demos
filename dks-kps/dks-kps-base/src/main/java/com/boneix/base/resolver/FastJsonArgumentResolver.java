package com.boneix.base.resolver;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.boneix.base.annotation.Json;
import com.boneix.base.util.StringUtils;

/**
 * fastJson注解处理类
 * 
 * @author iteye.zjumty
 * @version [1.0, 2016年1月10日]
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Json.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        String jsonString = getAllParam(webRequest);
        // 这边判断下jsonString是否为空
        if (StringUtils.isNotEmpty(jsonString)) {
            // 利用fastjson转换为对应的类型
            if (JSONObjectWrapper.class.isAssignableFrom(parameter
                    .getParameterType())) {
                return new JSONObjectWrapper(JSON.parseObject(jsonString));
            } else {
                return JSON.parseObject(jsonString, parameter.getParameterType());
            }
        } else {
            return null;
        }

    }

    /**
     * 获取HttpServletRequest参数体
     * 
     * @param webRequest
     * @return
     * @throws IOException
     */
    private String getAllParam(NativeWebRequest webRequest) throws IOException {
        HttpServletRequest httpServletRequest = webRequest
                .getNativeRequest(HttpServletRequest.class);
        String method = httpServletRequest.getMethod();
        if (method.equals("GET") || method.equals("DELETE")) {
            return httpServletRequest.getQueryString();
        }
        StringBuilder buffer = new StringBuilder();
        String line;
        BufferedReader reader = httpServletRequest.getReader();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
