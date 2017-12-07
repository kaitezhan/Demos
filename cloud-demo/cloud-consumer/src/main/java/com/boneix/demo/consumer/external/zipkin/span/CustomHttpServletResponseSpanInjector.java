package com.boneix.demo.consumer.external.zipkin.span;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by rzhang on 2017/12/6.
 */
@Slf4j
public class CustomHttpServletResponseSpanInjector implements SpanInjector<HttpServletResponse> {
    @Override
    public void inject(Span span, HttpServletResponse response) {
        response.addHeader("correlationId", Span.idToHex(span.getTraceId()));
        response.addHeader("mySpanId", Span.idToHex(span.getSpanId()));
        span.tag("response", getResponseContent(response));
    }

    private String getResponseContent(HttpServletResponse response) {
        String content = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            content = outputStream.toString();
        } catch (IOException e) {
            log.error("获取请求body失败{}", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return content;
    }
}
