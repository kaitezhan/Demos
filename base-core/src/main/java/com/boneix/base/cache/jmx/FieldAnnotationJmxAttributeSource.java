/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-10
 * Description:FieldAnnotationJmxAttributeSource.java 
 */
package com.boneix.base.cache.jmx;

import com.boneix.base.annotation.JmxAttribute;
import com.boneix.base.annotation.JmxOperation;
import com.boneix.base.annotation.JmxSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.annotation.AnnotationBeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.jmx.export.metadata.ManagedAttribute;
import org.springframework.jmx.export.metadata.ManagedOperation;
import org.springframework.jmx.export.metadata.ManagedResource;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchong
 */
public class FieldAnnotationJmxAttributeSource extends AnnotationJmxAttributeSource {
    private Map<String, Map<String, Field>> propertys = new HashMap<String, Map<String, Field>>();

    @Override
    public ManagedResource getManagedResource(Class<?> beanClass) throws InvalidMetadataException {
        ManagedResource managedResource = super.getManagedResource(beanClass);

        if (managedResource == null) {
            JmxSource jmxSource = AnnotationUtils.getAnnotation(beanClass, JmxSource.class);
            if (jmxSource != null) {
                managedResource = new ManagedResource();
                AnnotationBeanUtils.copyPropertiesToBean(jmxSource, managedResource);
            }
        }

        return managedResource;
    }

    @Override
    public ManagedOperation getManagedOperation(Method method) throws InvalidMetadataException {
        ManagedOperation managedOperation = super.getManagedOperation(method);

        if (managedOperation == null) {
            JmxOperation jmxOperation = method.getAnnotation(JmxOperation.class);
            System.err.println(method.getName() + "   " + jmxOperation);
            if (jmxOperation != null) {
                managedOperation = new ManagedOperation();
                AnnotationBeanUtils.copyPropertiesToBean(jmxOperation, managedOperation);
            }
        }

        return managedOperation;
    }

    private Field getFieldByMethod(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        if (propertys.get(clazz.getName()) == null) {
            propertys.put(clazz.getName(), new HashMap<String, Field>());
            for (Field field : clazz.getDeclaredFields()) {
                propertys.get(clazz.getName()).put(field.getName(), field);
            }
        }

        PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
        if (descriptor != null) {
            System.err.println(descriptor.getName() + " "
                    + propertys.get(clazz.getName()).containsKey(descriptor.getName()));
            return propertys.get(clazz.getName()).get(descriptor.getName());
        }
        return null;
    }

    @Override
    public ManagedAttribute getManagedAttribute(Method method) throws InvalidMetadataException {

        ManagedAttribute managedAttribute = super.getManagedAttribute(method);
        if (managedAttribute == null) {
            Field field = getFieldByMethod(method);
            if (field != null) {
                JmxAttribute ann = AnnotationUtils.getAnnotation(field, JmxAttribute.class);
                managedAttribute = new ManagedAttribute();
                AnnotationBeanUtils.copyPropertiesToBean(ann, managedAttribute, "defaultValue");
                if (ann.defaultValue().length() > 0) {
                    managedAttribute.setDefaultValue(ann.defaultValue());
                }
            }
        }

        return managedAttribute;
    }
}
