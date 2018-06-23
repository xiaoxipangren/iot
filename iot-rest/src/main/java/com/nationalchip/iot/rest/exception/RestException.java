package com.nationalchip.iot.rest.exception;

import com.nationalchip.iot.exception.UncheckedException;
import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/23/18 1:44 PM
 * @Modified:
 */
public class RestException extends UncheckedException {
    private HttpStatus httpStatus;

    public RestException(){
        super();
        this.httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RestException(String message){
        super(message);
        this.httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


    public RestException(String message,HttpStatus httpStatus){
        this(message);
        this.httpStatus=httpStatus;
    }


}
