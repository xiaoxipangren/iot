package com.nationalchip.iot.aspect;

import com.nationalchip.iot.constant.Constant;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 2:54 PM
 * @Modified:
 */
@Component
@Aspect
@Order(Constant.AspectOrder.GLOBAL)
/**
 * 整个项目的全局异常处理切面，对所有类所有方法进行过滤。
 * 子项目中的切面一定要保证order比0大
 */
public class GlobalExceptionLogAspect extends AbstractExceptionLogAspect{
//    @AutoLogger
//    protected org.slf4j.Logger logger;
//
//    @Pointcut("execution( * com.nationalchip.iot..*.*(..)) "
//            +"&& !@within(com.nationalchip.iot.annotation.Direct) "
//            +"&& !@within(org.springframework.security.annotation.Configuration) "
//            +"&& !@within(org.springframework.boot.security.properties.ConfigurationProperties) ")
//    public void exceptionLog(){ }
//
//    @AfterThrowing(pointcut = "exceptionLog()",throwing = "e")
//    public void logExcption(JoinPoint point, UncheckedException e){
//        log(point,e,logger);
//    }
}
