package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.rest.exception.RestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 1:39 PM
 * @Modified:
 */
public class Response {
    private boolean success=true;
    private int code;
    private String message;
    private Object data;

    public Response(){
        code=200;
        message="";
        data=new Object();
    }

    public Response(RestException e){
        this();
        code=e.getHttpStatus().value();
        success=false;
        message=e.getMessage();
    }

    public Response(String message){
        this();
        this.message=message;
    }

    public Response(String message, Object data){
        this(message);
        this.data=data;
    }

    public Response(Object data){
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

    public static Response success(String message){
        return new Response(message);
    }

    public static Response success(Object data){
       return new Response(data);
    }

    public static Response success(String message, Object data){
        return new Response(message,data);
    }

    public static Response success(String message, Object data, int code){
        Response response = new Response(message,data);
        response.setCode(code);
        return response;
    }

    public static Response error(String message, int errorCode){
        Response response = new Response(message);
        response.setCode(errorCode);
        response.setSuccess(false);
        return response;
    }

    public static Response error(RestException ex){
        return new Response(ex);
    }

    public static ResponseEntity<Response> ok(String message){
        return ResponseEntity.ok(success(message));
    }

    public static ResponseEntity<Response> error(String message){
        return new ResponseEntity<>(error(message, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Response> error(String message, HttpStatus status){
        return new ResponseEntity<>(error(message, status.value()), status);
    }

    public static ResponseEntity<Response> ok(Object data){
        return ResponseEntity.ok(Response.success(data));
    }
    public static ResponseEntity<Response> ok(String message, Object data){
        return ResponseEntity.ok(success(message,data));
    }

    public static ResponseEntity<Response> success(String message, Object data, HttpStatus status){
        return new ResponseEntity<>(success(message, data, status.value()), status);
    }

    public static ResponseEntity<Response> ok(String headerName, String headerValue){

        HttpHeaders headers = new HttpHeaders();
        headers.set(headerName,headerValue);

        return new ResponseEntity<>(success(""),headers,HttpStatus.OK);
    }


    public static ResponseEntity<Response> created(Object data){
        return success("资源创建成功",data,HttpStatus.CREATED);
    }

    public static ResponseEntity<Response> created(String message,Object data){
        return success(message,data,HttpStatus.CREATED);
    }

    public static ResponseEntity<Response> deleted(){
        return success("资源已删除",null,HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<Response> deleted(String message){
        return success(message,null,HttpStatus.NO_CONTENT);
    }
}
