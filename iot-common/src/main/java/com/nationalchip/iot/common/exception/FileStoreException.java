package com.nationalchip.iot.common.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 10:59 AM
 * @Modified:
 */
public class FileStoreException extends CommonException {
    public FileStoreException(String message) {
        super(message);
    }

    public FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStoreException(Throwable cause) {
        super(cause);
    }
}
