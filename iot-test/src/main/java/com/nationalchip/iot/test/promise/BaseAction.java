package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 10:31 AM
 * @Modified:
 */
public abstract class BaseAction<D,E extends Exception> implements IAction<D,E> {

    public D getData() {
        return data;
    }

    private E ex;

    public E getEx() {
        return ex;
    }

    public void setEx(E ex) {
        this.ex = ex;
    }

    public void setData(D data) {
        this.data = data;
    }

    private D data;

    public BaseAction(D data){
        this.data=data;
    }

    public BaseAction(E ex){
        this.ex=ex;
    }


}
