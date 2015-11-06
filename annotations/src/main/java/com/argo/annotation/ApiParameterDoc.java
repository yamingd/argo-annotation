package com.argo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yamingd on 10/15/15.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface ApiParameterDoc {
    /**
     * 说明
     * @return String
     */
    String value();

    /**
     * 参数是否是必须的
     * @return boolean
     */
    boolean required() default false;
}
