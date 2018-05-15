package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 10:19 AM
 * @Modified:
 */
public interface IPromise<D,E extends Exception> {
    IPromise then(Consumer<D> success);
    IPromise error(Consumer<E> error);
}
