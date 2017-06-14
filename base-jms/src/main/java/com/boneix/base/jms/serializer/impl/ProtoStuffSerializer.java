package com.boneix.base.jms.serializer.impl;

import com.boneix.base.jms.serializer.ISerializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by rzhang on 2017/6/2.
 */
public class ProtoStuffSerializer implements ISerializer {

    private static Map<Class<?>, Schema<?>> cacheSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    @Override
    public <T> byte[] writeObject(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getCacheSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        try {
            T message = objenesis.newInstance(clazz);
            Schema<T> schema = getCacheSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private <T> Schema<T> getCacheSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cacheSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cacheSchema.put(cls, schema);
        }
        return schema;
    }
}
