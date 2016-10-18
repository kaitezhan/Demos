package com.boneix.base.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 基于fastJson的Json工具类
 *
 * @author boneix
 * @version [1.0, 2016年1月13日]
 */
public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    /**
     * 将传入的对象转换成指定的对象
     *
     * @param obj   源对象
     * @param clazz 类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Object obj, Class<T> clazz) {
        if (null == obj) {
            return null;
        }
        if (clazz == String.class) {
            return (T) (toString(obj));
        }
        try {
            return JSON.parseObject(JsonUtils.toString(obj), clazz);
        } catch (Exception e) {
            LOG.error("JsonUtils.toBean error: {}", e);
        }
        return null;

    }

    /**
     * 将Object转化为Json格式字符串
     *
     * @param obj 源对象
     * @return String
     */
    public static String toString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (null == obj) {
            return null;
        }
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            LOG.error("JsonUtils.toString error: {}", e);
        }
        return null;
    }

    /**
     * 将Object转化为HashMap
     *
     * @param obj 源对象
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            return JsonUtils.toBean(obj, HashMap.class);
        } catch (Exception e) {
            LOG.error("JsonUtils.toMap error: {}", e);
        }
        return null;
    }

    /**
     * 将Object转化为包含Map的List
     *
     * @param obj 源对象
     * @return ListWithMap
     */
    @SuppressWarnings("unchecked")
    public static <K, V> List<Map<K, V>> toListWithMap(Object obj) {
        List<Map<K, V>> lists = new LinkedList<Map<K, V>>();
        List<Object> list = JsonUtils.toBean(obj, List.class);
        if (null != list) {
            for (Object object : list) {
                Map<K, V> map = JsonUtils.toMap(object);
                if (null != map) {
                    lists.add(map);
                }
            }
        }
        return lists;
    }

    /**
     * 将Object转化为包含cls的List
     *
     * @param obj 源对象
     * @param cls 类型
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Object obj, Class<T> cls) {
        List<T> lists = new LinkedList<T>();
        List<Object> list = JsonUtils.toBean(obj, List.class);
        if (null != list) {
            for (Object object : list) {
                T t = JsonUtils.toBean(object, cls);
                if (null != t) {
                    lists.add(t);
                }
            }
        }
        return lists;
    }
}
