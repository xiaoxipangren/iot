package com.nationalchip.iot.message.exception;

import com.nationalchip.iot.exception.UncheckedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 10:55 AM
 * @Modified:
 */
public class MessageException extends UncheckedException {
    public MessageException(String message){
        super(message);
    }

    public MessageException(String message,Throwable cause){
        super(message,cause);
    }
}
