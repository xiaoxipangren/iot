package com.nationalchip.iot.log;

import com.nationalchip.iot.annotation.AutoLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 10:47 AM
 * @Modified:
 */
public class LoggerInjector implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(final Object bean,final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(AutoLogger.class) != null) {
                    org.slf4j.Logger log = LoggerFactory.getLogger(bean.getClass());
                    field.set(bean, log);
                }
            }
        });
        return bean;
    }
}
