package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 4:56 PM
 * @Modified:
 */
public class ResolveAction<D,E extends Exception> extends BaseAction<D,E> {


    public ResolveAction(D data) {
        super(data);
    }


    @Override
    public void action(Consumer<D> resolve, Consumer<E> reject) {
        new Thread(() ->{
            try {
                Thread.sleep(3000);
                resolve.accept(getData());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
