package com.boneix.client.external.zipkin.span;

import com.boneix.client.tools.HttpContentUtil;
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
        log.info("inject");
        response.addHeader("correlationId", Span.idToHex(span.getTraceId()));
        response.addHeader("mySpanId", Span.idToHex(span.getSpanId()));
        span.tag("response", HttpContentUtil.getResponseContent(response));
    }


}
