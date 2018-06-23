package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 3:40 PM
 * @Modified:
 */
public class IdNotSetException extends RestException {
    public IdNotSetException(String message){
        super(message,HttpStatus.BAD_REQUEST);
    }
}
