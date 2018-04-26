package com.nationalchip.iot.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/8/18 11:13 AM
 * @Modified:
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Comment
public @interface Model {
    @AliasFor(annotation = Table.class,attribute = "name")
    String value();

    @AliasFor(annotation = Comment.class,attribute = "value")
    String comment() default "";

    @AliasFor(annotation = Entity.class,attribute = "name")
    String entity() default "";
}
