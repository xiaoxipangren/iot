package com.nationalchip.iot.annotation;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 2:51 PM
 * @Modified:
 */
import java.lang.annotation.*;


/**
 * Indicate class will be used directly instead of being proxied.
 */
@Inherited
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Direct {
}