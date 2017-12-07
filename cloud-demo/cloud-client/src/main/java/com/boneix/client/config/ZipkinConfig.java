package com.boneix.client.config;

import com.boneix.client.external.zipkin.SpanTraceFilter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipkinConfig {

//    @Bean
//    public SpanInjector<HttpServletResponse> httpServletResponseSpanInjector() {
//        return new CustomHttpServletResponseSpanInjector();
//    }
//
//
//    @Bean
//    public SpanExtractor<HttpServletRequest> httpServletRequestSpanExtractor() {
//        return new CustomHttpServletRequestSpanExtractor();
//    }

    @Bean
    TraceFilter createTraceFilter(BeanFactory beanFactory, final Tracer tracer) {
        return new SpanTraceFilter(beanFactory, tracer);
    }

}