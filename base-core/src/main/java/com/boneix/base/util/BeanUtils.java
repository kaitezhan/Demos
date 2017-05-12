package com.boneix.base.util;

import org.apache.commons.beanutils.BeanMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 对Bean进行操作的相关工具方法
 *
 * @author hu_guodong
 * @version [1.0, 2014年12月8日]
 */
public class BeanUtils {
    /**
     * 将Bean对象转换成Map对象，将忽略掉值为null或size=0的属性
     *
     * @param obj 对象
     * @return 若给定对象为null则返回size=0的map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        BeanMap beanMap = new BeanMap(obj);
        Iterator<String> iterator = beanMap.keyIterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object value = beanMap.get(name);
            // 转换时会将类名也转换成属性，此处去掉
            if (value != null && !name.equals("class")) {
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 该方法将给定的所有对象参数列表转换合并生成一个Map，对于同名属性，依次由后面替换前面的对象属性
     *
     * @param objs 对象列表
     * @return 对于值为null的对象将忽略掉
     */
    public static Map<String, Object> toMap(Object... objs) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object object : objs) {
            if (object != null) {
                map.putAll(toMap(object));
            }
        }
        return map;
    }

    /**
     * 获取接口的泛型类型，如果不存在则返回null
     *
     * @param clazz clazz
     * @return 泛型类型
     */
    public static Class<?> getGenericClass(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedTypes = ((ParameterizedType) type).getActualTypeArguments();
            return ((Class<?>) parameterizedTypes[0]);
        }
        return null;
    }
}