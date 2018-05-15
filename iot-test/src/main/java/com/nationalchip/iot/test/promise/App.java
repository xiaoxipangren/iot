package com.nationalchip.iot.test.promise;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 11:12 AM
 * @Modified:
 */
public class App {
    public static void main(String args[]){
        IAction<String,PromiseException> action = new PromiseAction("abc");
        IPromise<String,PromiseException> promise = Promise.promise(action);

        promise.error(ex -> System.out.println(ex.getMessage()))
                .then(d -> System.out.println(d));

//        action = new PromiseAction("def");
//
//        promise = Promise.promise(action);
//
//        promise.error(ex -> System.out.println(ex.getMessage()))
//                .then(d -> System.out.println(d));



    }
}
