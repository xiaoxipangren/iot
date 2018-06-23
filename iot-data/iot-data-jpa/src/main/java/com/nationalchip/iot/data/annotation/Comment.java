package com.nationalchip.iot.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于解释实体字段的含义
 */

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    String value()default "";
}
