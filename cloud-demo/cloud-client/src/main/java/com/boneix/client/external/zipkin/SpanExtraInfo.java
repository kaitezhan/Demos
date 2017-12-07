package com.boneix.client.external.zipkin;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/7/6.
 */
public class SpanExtraInfo {

    private static final ThreadLocal<SpanExtraInfo> SPAN_EXTRA_INFO_THREAD_LOCAL = new ThreadLocal<>();

    private String requestHeaders;

    private String requestParams;

    private String requestBody;

//    private String responseHeaders;
//
//    private String responseBody;

    public static SpanExtraInfo getInstance() {
        return SPAN_EXTRA_INFO_THREAD_LOCAL.get();
    }

    public static void clean() {
        SPAN_EXTRA_INFO_THREAD_LOCAL.remove();
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public String getRequestBody() {
        return requestBody;
    }

//    public String getResponseHeaders() {
//        return responseHeaders;
//    }
//
//    public String getResponseBody() {
//        return responseBody;
//    }

    private SpanExtraInfo(SpanExtraInfoBuild build) {
        this.requestHeaders = build.requestHeaders;
        this.requestParams = build.requestParams;
        this.requestBody = build.requestBody;
//        this.responseHeaders = build.responseHeaders;
//        this.responseBody = build.responseBody;
        setContext(this);
    }

    private void setContext(SpanExtraInfo context) {
        SPAN_EXTRA_INFO_THREAD_LOCAL.set(context);
    }

    public static class SpanExtraInfoBuild {

        private String requestHeaders;

        private String requestParams;

        private String requestBody;

//        private String responseHeaders;
//
//        private String responseBody;


        public SpanExtraInfoBuild requestHeaders(String requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }

        public SpanExtraInfoBuild requestParams(String requestParams) {
            this.requestParams = requestParams;
            return this;
        }

        public SpanExtraInfoBuild requestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }

//        public SpanExtraInfoBuild responseHeaders(String responseHeaders) {
//            this.responseHeaders = responseHeaders;
//            return this;
//        }
//
//        public SpanExtraInfoBuild responseBody(String responseBody) {
//            this.responseBody = responseBody;
//            return this;
//        }

        public SpanExtraInfo build() {
            return new SpanExtraInfo(this);
        }

    }
}