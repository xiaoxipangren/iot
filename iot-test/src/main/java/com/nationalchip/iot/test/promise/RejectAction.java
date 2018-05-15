package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 5:00 PM
 * @Modified:
 */
public class RejectAction<D,E extends Exception> extends BaseAction<D,E> {

    private E ex;

    public RejectAction(E ex){
        super(ex);
    }

    @Override
    public void action(Consumer<D> resolve, Consumer<E> reject) {
        new Thread(()->{

            try {
                Thread.sleep(3000);
                reject.accept(getEx());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
