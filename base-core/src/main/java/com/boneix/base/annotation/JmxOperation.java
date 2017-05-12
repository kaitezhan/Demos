/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-10
 * Description:JmxOperation.java 
 */
package com.boneix.base.annotation;

import java.lang.annotation.*;

/**
 * @author wangchong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JmxOperation {
    String description() default "";

    int currencyTimeLimit() default -1;
}
