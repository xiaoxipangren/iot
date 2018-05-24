package com.nationalchip.iot.data.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 2:00 PM
 * @Modified:
 */
public class ResourceStoreException extends DataException {
    public ResourceStoreException(String message){
        super(message);
    }

    public ResourceStoreException(String message,Throwable cause){
        super(message,cause);
    }
}
