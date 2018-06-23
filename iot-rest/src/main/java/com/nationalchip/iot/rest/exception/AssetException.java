package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 6:34 PM
 * @Modified:
 */
public class AssetException extends RestException{
    public AssetException(){
        super();

    }

    public AssetException(String message){
        super(message);
    }


    public AssetException(String message, HttpStatus httpStatus){
        super(message,httpStatus);
    }
}
