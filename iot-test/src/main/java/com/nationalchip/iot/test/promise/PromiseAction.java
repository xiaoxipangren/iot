package com.nationalchip.iot.test.promise;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 5:10 PM
 * @Modified:
 */
public class PromiseAction extends BaseAction<String,PromiseException> {
    public PromiseAction(String data) {
        super(data);
    }

    @Override
    public void action(Consumer<String> resolve, Consumer<PromiseException> reject) {
        new Thread(() -> {
            if(getData().contains("a")){
                resolve.accept(getData());
            }
            else {
                reject.accept(new PromiseException(String.format("%s not contains a",getData())));
            }
        }).start();
    }
}
