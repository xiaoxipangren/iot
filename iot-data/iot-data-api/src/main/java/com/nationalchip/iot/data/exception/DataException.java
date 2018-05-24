package com.nationalchip.iot.data.exception;

import com.nationalchip.iot.exception.UncheckedException;

import javax.xml.crypto.Data;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 1:54 PM
 * @Modified:
 */
public class DataException extends UncheckedException {
    public DataException(String message){
        super(message);
    }

    public DataException(String message,Throwable cause){
        super(message,cause);
    }

    public DataException(Throwable cause){
        super(cause);
    }
}
