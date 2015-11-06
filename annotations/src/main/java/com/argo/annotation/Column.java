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
public @interface Column {
    /**
     * 是否是主键
     * @return boolean
     */
    boolean pk() default false;
    /**
     * 字段名，通常和属性名保持一致
     * @return String
     */
    String name() default "";

    /**
     *
     * 默认值 now0, now1, today, number, empty, true, false, CURRENT_TIMESTAMP, NULL
     * @return String
     */
    String defaultVal() default "";

    /**
     * 自增列
     * @return boolean
     */
    boolean autoIncrement() default false;

    /**
     * 最大长度
     * @return String
     */
    String maxLength() default "";

    /**
     * 是否可为NULL
     * @return boolean
     */
    boolean nullable() default true;
}
