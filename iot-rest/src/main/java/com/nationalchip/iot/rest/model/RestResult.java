package com.nationalchip.iot.rest.model;

import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 1:39 PM
 * @Modified:
 */
public class RestResult {
    private boolean success=true;
    private int code;
    private String message;
    private Object data;

    public RestResult(){
        code=200;
        message="";
        data=new Object();
    }

    public RestResult(RestException e){
        this();
        code=e.getHttpStatus().value();
        success=false;
        message=e.getMessage();
    }

    public RestResult(String message){
        this();
        this.message=message;
    }

    public RestResult(String message,Object data){
        this(message);
        this.data=data;
    }

    public RestResult(Object data){
        this("Request successfully.",data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseEntity<RestResult> success(String message){
        return new ResponseEntity<RestResult>(new RestResult(message),HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> success(Object data){
        return new ResponseEntity<RestResult>(new RestResult(data),HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> success(String message,Object data){
        return new ResponseEntity<RestResult>(new RestResult(message,data),HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> success(String message,Object data,int code){
        RestResult restResult = new RestResult(message,data);
        restResult.setCode(code);
        return new ResponseEntity<RestResult>(restResult,HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> error(RestException e){
        return new ResponseEntity<RestResult>(new RestResult(e),e.getHttpStatus());
    }

}
