package com.boneix.client.external.zipkin;

import com.boneix.client.tools.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;


/**
 * Created by rzhang on 2017/12/7.
 */
@Slf4j
public class SpanTraceFilter extends TraceFilter {

    private Tracer tracer;

    public SpanTraceFilter(BeanFactory beanFactory, Tracer tracer) {
        this(beanFactory);
        this.tracer = tracer;
    }

    private SpanTraceFilter(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("Filter just supports HTTP requests");
        }
        // 切入
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // 截取request值
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            requestHeaders.add(headerName, requestWrapper.getHeader(headerName));
        }
        Map<String, String[]> requestParams = requestWrapper.getParameterMap();
        String requestBody = IOUtils.toString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);

        try {
            new SpanExtraInfo
                    .SpanExtraInfoBuild()
                    .requestHeaders(JSONUtils.toJson(requestHeaders))
                    .requestParams(JSONUtils.toJson(requestParams))
                    .requestBody(requestBody)
                    .build();
        } catch (JsonProcessingException exc) {
            log.warn("request info analyze error! {}", exc);
        }

        // 调用父
        super.doFilter(requestWrapper, responseWrapper, filterChain);
    }

    @Override
    protected void addResponseTags(HttpServletResponse response, Throwable e) {
        super.addResponseTags(response, e);

        SpanExtraInfo spanExtraInfo = SpanExtraInfo.getInstance();
        tracer.addTag("requestHeaders", spanExtraInfo.getRequestHeaders());
        tracer.addTag("requestParams", spanExtraInfo.getRequestParams());
        tracer.addTag("requestBody", spanExtraInfo.getRequestBody());
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
            HttpHeaders responseHeaders = new HttpHeaders();
            for (String headerName : responseWrapper.getHeaderNames()) {
                responseHeaders.add(headerName, responseWrapper.getHeader(headerName));
            }
            try {
                tracer.addTag("responseHeaders", JSONUtils.toJson(responseHeaders));
            } catch (JsonProcessingException exc) {
                log.warn("responseHeaders analyze error! {}", exc);
            }
            try {
                String responseBody = IOUtils.toString(responseWrapper.getContentInputStream(), StandardCharsets.UTF_8);
                tracer.addTag("responseBody", responseBody);
                // 最后需要重新写下servletResponse
                responseWrapper.copyBodyToResponse();
            } catch (IOException exc) {
                log.warn("responseBody analyze error! {}", exc);
            }
        }
    }
}
