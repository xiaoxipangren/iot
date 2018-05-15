package com.nationalchip.iot.rest.model;

import com.nationalchip.iot.rest.exception.RestException;

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

    public static RestResult success(String message){
        return new RestResult(message);
    }

    public static RestResult success(Object data){
       return new RestResult(data);
    }

    public static RestResult success(String message,Object data){
        return new RestResult(message,data);
    }

    public static RestResult success(String message,Object data,int code){
        RestResult restResult = new RestResult(message,data);
        restResult.setCode(code);
        return restResult;
    }

    public static RestResult error(String message,int errorCode){
        RestResult restResult = new RestResult(message);
        restResult.setCode(errorCode);
        restResult.setSuccess(false);
        return restResult;
    }

    public static RestResult error(RestException ex){
        return new RestResult(ex);
    }
}
