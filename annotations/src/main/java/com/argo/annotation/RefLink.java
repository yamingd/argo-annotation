package com.argo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yamingd on 9/15/15.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface RefLink {

    /**
     * Ref Column 的 属性名
     * @return String
     */
    String on() default "";
}
