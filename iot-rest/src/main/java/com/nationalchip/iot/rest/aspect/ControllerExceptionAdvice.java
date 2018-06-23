package com.nationalchip.iot.rest.aspect;

import com.nationalchip.iot.annotation.Direct;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.resource.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<Response> restExceptionHandle(RestException ex){
        return new ResponseEntity<>(Response.error(ex),ex.getHttpStatus());
    }

}
