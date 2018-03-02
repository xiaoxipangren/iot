package com.nationalchip.iot.rest.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 1:39 PM
 * @Modified:
 */
public class RestResult<T> extends ResponseEntity<T> {

    private String message;
    private boolean successed;


    public RestResult(String message,HttpStatus status){
        this(status);
        this.message=message;

    }

    public RestResult(HttpStatus status) {
        super(status);
    }

    public RestResult(T body, HttpStatus status) {
        super(body, status);
    }

    public RestResult(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public RestResult(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    private void isSecceed(HttpStatus status){

        this.successed=status.value()>200 && status.value()<300;

    }
}
