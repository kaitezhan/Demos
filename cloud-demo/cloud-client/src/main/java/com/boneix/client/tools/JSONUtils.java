package com.boneix.client.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/7/13.
 */
public final class JSONUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JSONUtils() {
    }

    public static <T> T toBean(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static String toJson(Object bean) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(bean);
    }
}