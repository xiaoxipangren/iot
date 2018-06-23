package com.nationalchip.iot.common.exception;

import com.nationalchip.iot.exception.UncheckedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 10:49 AM
 * @Modified:
 */
public class CommonException extends UncheckedException {
    public CommonException(String message){
        super(message);
    }

    public CommonException(String message,Throwable cause){
        super(message,cause);
    }

    public CommonException(Throwable cause){
        super(cause);
    }
}
