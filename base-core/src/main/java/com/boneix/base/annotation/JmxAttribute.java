/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-10
 * Description:ManagedAttribute.java 
 */
package com.boneix.base.annotation;

import java.lang.annotation.*;

/**
 * @author wangchong
 */
@Target({
        ElementType.FIELD, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JmxAttribute {

    String defaultValue() default "";

    String description() default "";

    int currencyTimeLimit() default -1;

    String persistPolicy() default "";

    int persistPeriod() default -1;

}
