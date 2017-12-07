package com.boneix.demo.consumer.config;

import com.boneix.demo.consumer.external.zipkin.span.CustomHttpServletRequestSpanExtractor;
import com.boneix.demo.consumer.external.zipkin.span.CustomHttpServletResponseSpanInjector;
import org.springframework.cloud.sleuth.SpanExtractor;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class ZipkinConfig {

    @Bean
    public SpanInjector<HttpServletResponse> httpServletResponseSpanInjector() {
        return new CustomHttpServletResponseSpanInjector();
    }


    @Bean
    public SpanExtractor<HttpServletRequest> httpServletRequestSpanExtractor() {
        return new CustomHttpServletRequestSpanExtractor();
    }

}