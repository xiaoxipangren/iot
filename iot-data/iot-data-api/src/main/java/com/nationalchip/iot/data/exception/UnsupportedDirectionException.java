package com.nationalchip.iot.data.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/26/18 12:51 PM
 * @Modified:
 */
public class UnsupportedDirectionException extends DataException {

    public UnsupportedDirectionException(){
        this("不支持的排序方向");
    }

    public UnsupportedDirectionException(String message) {
        super(message);
    }

    public UnsupportedDirectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDirectionException(Throwable cause) {
        super(cause);
    }
}
