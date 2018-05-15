package com.nationalchip.iot.test.promise;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 10:21 AM
 * @Modified:
 */
public class PromiseException extends Exception{
    private String message;
    public PromiseException(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
