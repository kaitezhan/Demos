package com.boneix.demo.consumer.external.zipkin.span;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanExtractor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rzhang on 2017/12/6.
 */
@Slf4j
public class CustomHttpServletRequestSpanExtractor implements SpanExtractor<HttpServletRequest> {

    private static final String JSON_BODY_ATTRIBUTE = "JSON_REQUEST_BODY";
    private static final String EMPTY_JSON = "{}";

    @Override
    public Span joinTrace(HttpServletRequest request) {
        long traceId = Span.hexToId(request.getHeader("correlationId"));
        long spanId = Span.hexToId(request.getHeader("mySpanId"));
        // extract all necessary headers
        Span.SpanBuilder builder = Span.builder().traceId(traceId).spanId(spanId);
        // build rest of the Span
        builder.tag("request", getRequestContent(request));
        return builder.build();
    }


    /**
     * 获取请求内容，如果是get请求则为空
     *
     * @param request 请求对象
     * @return 签名内容
     */
    private String getRequestContent(HttpServletRequest request) {
        String content = null;
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                content = IOUtils.toString(inputStream, "utf-8");
            } catch (IOException e) {
                log.error("获取请求body失败{}", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        } else {
            content = request.getQueryString();
        }
        if (StringUtils.isBlank(content) || EMPTY_JSON.equals(content)) {
            content = StringUtils.EMPTY;
        }
        return content;
    }
}
