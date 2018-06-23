package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 3:21 PM
 * @Modified:
 */
public class NewsException extends RestException{
    public NewsException(){
        super();

    }

    public NewsException(String message){
        super(message);
    }


    public NewsException(String message,HttpStatus httpStatus){
        super(message,httpStatus);
    }
}
