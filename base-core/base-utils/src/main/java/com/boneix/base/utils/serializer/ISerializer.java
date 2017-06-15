package com.boneix.base.utils.serializer;

/**
 * Created by rzhang on 2017/6/2.
 */
public interface ISerializer {
    /**
     * 将对象序列化成byte[]
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] writeObject(T obj);

    /**
     * 将byte数组反序列成对象
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T readObject(byte[] bytes, Class<T> clazz);
}
