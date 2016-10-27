/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-10
 * Description:JmxSource.java 
 */
package com.boneix.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangchong
 * @see org.springframework.jmx.export.annotation.ManagedResource
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JmxSource {
    /**
     * The annotation value is equivalent to the {@code objectName} attribute, for simple default usage.
     */
    String value() default "";

    String objectName() default "";

    String description() default "";

    int currencyTimeLimit() default -1;

    boolean log() default false;

    String logFile() default "";

    String persistPolicy() default "";

    int persistPeriod() default -1;

    String persistName() default "";

    String persistLocation() default "";
}
