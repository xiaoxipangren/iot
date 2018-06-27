package com.nationalchip.iot.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/24/18 1:19 PM
 * @Modified:
 */
public class FileReadException extends RestException {
    public FileReadException() {
    }

    public FileReadException(String message) {
        super(message);
    }

    public FileReadException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
