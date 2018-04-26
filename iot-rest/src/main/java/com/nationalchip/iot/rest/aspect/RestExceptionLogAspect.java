package com.nationalchip.iot.rest.aspect;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.aspect.AbstractExceptionLogAspect;
import com.nationalchip.iot.exception.UncheckedException;
import com.nationalchip.iot.constant.Constant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 3:02 PM
 * @Modified:
 */
@Component
@Aspect
@Order(Constant.AspectOrder.REST)
public class RestExceptionLogAspect extends AbstractExceptionLogAspect {


    @AutoLogger
    protected org.slf4j.Logger logger;

    @Pointcut("execution(public * com.nationalchip.iot.rest..*.*(..)) && !@within(com.nationalchip.iot.annotation.Direct)")
    public void exceptionLog(){ }

    @AfterThrowing(pointcut = "exceptionLog()",throwing = "e")
    public void logExcption(JoinPoint point, UncheckedException e){
        log(point,e,logger);
    }
}

