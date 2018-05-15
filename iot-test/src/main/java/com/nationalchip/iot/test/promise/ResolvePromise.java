package com.nationalchip.iot.test.promise;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 4:35 PM
 * @Modified:
 */
public class ResolvePromise<D,E extends Exception> extends BasePromise<D,E> {
    protected ResolvePromise(IAction<D, E> action) {
        super(action);
    }


    @Override
    public IPromise error(Consumer<E> error) {
        throw new NotImplementedException();
    }
}
