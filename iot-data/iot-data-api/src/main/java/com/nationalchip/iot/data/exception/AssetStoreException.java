package com.nationalchip.iot.data.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 2:00 PM
 * @Modified:
 */
public class AssetStoreException extends DataException {
    public AssetStoreException(String message){
        super(message);
    }

    public AssetStoreException(String message, Throwable cause){
        super(message,cause);
    }
}
