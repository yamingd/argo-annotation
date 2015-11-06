package com.argo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yamingd on 9/15/15.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Table {

    /**
     * Table Name
     * @return String
     */
    String value();
}
