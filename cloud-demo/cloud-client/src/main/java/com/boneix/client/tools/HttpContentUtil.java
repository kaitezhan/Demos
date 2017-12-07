package com.boneix.client.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rzhang on 2017/12/7.
 */
@Slf4j
public class HttpContentUtil {


    private static final String JSON_BODY_ATTRIBUTE = "JSON_REQUEST_BODY";
    private static final String EMPTY_JSON = "{}";

    /**
     * 获取请求内容，如果是get请求则为空
     *
     * @param request 请求对象
     * @return 签名内容
     */
    public static String getRequestContent(HttpServletRequest request) {
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

    public static String getResponseContent(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
        return new String(wrapper.getContentAsByteArray());
    }
}
