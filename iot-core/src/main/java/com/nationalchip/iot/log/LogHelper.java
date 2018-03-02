package com.nationalchip.iot.log;

import org.slf4j.Logger;

public class LogHelper {
    public static void debug(Logger logger,String log){
        if(logger.isDebugEnabled())
            logger.debug(log);
    }

    public static void info(Logger logger,String log){
        if(logger.isInfoEnabled())
            logger.info(log);
    }

    public static void error(Logger logger,String log){
        if(logger.isErrorEnabled())
            logger.error(log);
    }

    public static void debug(Logger logger,String log,Throwable e){
        if(logger.isDebugEnabled())
            logger.debug(log,e);
    }

    public static void info(Logger logger,String log,Throwable e){
        if(logger.isInfoEnabled())
            logger.info(log,e);
    }

    public static void error(Logger logger,String log,Throwable e){
        if(logger.isErrorEnabled())
            logger.error(log,e);
    }
}
