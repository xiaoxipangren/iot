package com.nationalchip.iot.rest.aspect;

import com.nationalchip.iot.annotation.Direct;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.model.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 3:05 PM
 * @Modified:
 */
@ControllerAdvice
@Direct
public class ControllerExceptionAdvice {
    @ExceptionHandler(RestException.class)
    @ResponseBody
    public ResponseEntity<RestResult> restExceptionHandle(RestException ex){
        return new ResponseEntity<>(RestResult.error(ex),ex.getHttpStatus());
    }
}
