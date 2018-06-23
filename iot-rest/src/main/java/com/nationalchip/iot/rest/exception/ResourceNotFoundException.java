package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/23/18 4:06 PM
 * @Modified:
 */
public class ResourceNotFoundException extends RestException {
    public ResourceNotFoundException(String message){
        super(message,HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource,long id){
        this(String.format("id为%d的%s不存在",id,resource));
    }

    public ResourceNotFoundException(){
        super("资源不存在",HttpStatus.NOT_FOUND);
    }
}
