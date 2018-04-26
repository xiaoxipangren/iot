package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.rest.model.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 2:32 PM
 * @Modified:
 */
public abstract class BaseController {

    protected ResponseEntity<RestResult> ok(String message){
        return ResponseEntity.ok(RestResult.success(message));
    }

    protected ResponseEntity<RestResult> error(String message){
        return new ResponseEntity<>(RestResult.error(message,HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<RestResult> ok(Object data){
        return ResponseEntity.ok(RestResult.success(data));
    }
    protected ResponseEntity<RestResult> ok(String message,Object data){
        return ResponseEntity.ok(RestResult.success(message,data));
    }
}
