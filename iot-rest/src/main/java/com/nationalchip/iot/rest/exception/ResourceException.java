package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 6:34 PM
 * @Modified:
 */
public class ResourceException extends RestException{
    public ResourceException(){
        super();

    }

    public ResourceException(String message){
        super(message);
    }


    public ResourceException(String message,HttpStatus httpStatus){
        super(message,httpStatus);
    }
}
