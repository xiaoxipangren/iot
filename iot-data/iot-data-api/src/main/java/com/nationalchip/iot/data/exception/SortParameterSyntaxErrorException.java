package com.nationalchip.iot.data.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/28/18 9:40 AM
 * @Modified:
 */
public class SortParameterSyntaxErrorException extends DataException {
    public SortParameterSyntaxErrorException(String message) {
        super(message);
    }

    public SortParameterSyntaxErrorException(){
        this("排序参数格式错误");
    }

}
