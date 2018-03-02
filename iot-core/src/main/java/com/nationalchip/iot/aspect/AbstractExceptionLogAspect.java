package com.nationalchip.iot.aspect;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 2:52 PM
 * @Modified:
 */

import com.nationalchip.iot.annotation.Direct;
import com.nationalchip.iot.exception.UncheckedException;
import com.nationalchip.iot.log.LogHelper;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;

@Direct
public abstract class AbstractExceptionLogAspect{

    protected void log(JoinPoint point, UncheckedException exception,Logger logger){
        log(point,exception,logger,false);
    }

    protected void log(JoinPoint point, UncheckedException exception, Logger logger, boolean repeat){
        if(repeat || !exception.logged()){
            log(point.getSourceLocation()+": "+exception.log(),logger);
        }
    }

    protected void log(UncheckedException exception, Logger logger){
        log(exception,logger,false);
    }

    protected void log (UncheckedException exception, Logger logger,boolean repeat){
        if(repeat || !exception.logged()){
            log(exception.log(),logger);
        }
    }

    protected void logDetail(UncheckedException exception,Logger logger){
        logDetail(exception,logger,false);
    }

    protected void logDetail(UncheckedException exception,Logger logger,boolean repeat){
        if(repeat || !exception.logged())
            LogHelper.error(logger,exception.getMessage(),exception);
    }

    private void log(String log,Logger logger){
        LogHelper.error(logger,log);
    }

}
