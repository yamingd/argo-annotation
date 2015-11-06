package com.argo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yamingd on 10/15/15.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ApiMethodDoc {
    /**
     * 主体说明
     * @return String
     */
    String value();

    /**
     * 描述
     * @return String
     */
    String description() default "";

    /**
     * 版本号
     * @return String
     */
    String version() default "";

    /**
     * 返回的数据类型对应的Protobuf定义文件
     * @return String
     */
    String proto() default "";

    /**
     * 返回数据类型
     * @return String
     */
    Class<?> returnClass();
}
