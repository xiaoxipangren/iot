package com.nationalchip.iot.rest.aspect;

import com.nationalchip.iot.annotation.Direct;
import org.springframework.http.HttpStatus;
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
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(ToolNotFoundException.class)
//    @ResponseBody
//    public RestResult toolNotFound(Exception ex){
//        return new RestResult(ex);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({FtpIoException.class, MediaUploadException.class})
//    @ResponseBody
//    public RestResult mediaException(Exception ex){
//        return new RestResult(ex);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ToolExecutionException.class)
//    @ResponseBody
//    public RestResult rpcException(Exception ex){
//        return new RestResult(ex);
//    }

}
