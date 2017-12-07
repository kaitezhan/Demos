package com.boneix.client.external.zipkin.span;

import com.boneix.client.tools.HttpContentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rzhang on 2017/12/6.
 */
@Slf4j
public class CustomHttpServletRequestSpanExtractor implements SpanExtractor<HttpServletRequest> {


    @Override
    public Span joinTrace(HttpServletRequest request) {
        log.info("joinTrace");
        long traceId = Span.hexToId(request.getHeader("correlationId"));
        long spanId = Span.hexToId(request.getHeader("mySpanId"));
        // extract all necessary headers
        Span.SpanBuilder builder = Span.builder().traceId(traceId).spanId(spanId);
        // build rest of the Span
        builder.tag("request", HttpContentUtil.getRequestContent(request));
        return builder.build();
    }


}
