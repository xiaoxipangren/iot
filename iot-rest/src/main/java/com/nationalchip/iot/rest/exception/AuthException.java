package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/23/18 2:31 PM
 * @Modified:
 */
public class AuthException extends RestException {
    public AuthException(String message, HttpStatus httpStatus){
        super(message,httpStatus);
    }

    public AuthException(String message){
        super(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
