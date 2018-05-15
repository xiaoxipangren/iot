package com.nationalchip.iot.test.promise;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 4:20 PM
 * @Modified:
 */
public class RejectPromise<D,E extends Exception> extends BasePromise<D,E> {

    protected RejectPromise(IAction<D,E> action){
        super(action);
    }


    @Override
    public IPromise then(Consumer<D> success) {
        throw new NotImplementedException();
    }
}
