package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 10:25 AM
 * @Modified:
 */
public interface IAction<D, E extends Exception> {
    void action(Consumer<D>resolve, Consumer<E> reject);
}
