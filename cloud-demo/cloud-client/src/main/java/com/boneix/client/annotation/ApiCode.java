package com.boneix.client.annotation;

import java.lang.annotation.*;

/**
 * Created by rzhang on 2017/11/24.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiCode {
    /**
     * Api Path
     *
     * @return
     */
    String value() default "";

    /**
     * Api skip
     * @return
     */
    boolean skip() default false;
}
