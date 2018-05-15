package com.nationalchip.iot.test.promise;


/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 10:22 AM
 * @Modified:
 */
public class Promise<D,E extends Exception> extends BasePromise<D,E> {

    private Promise(IAction<D, E> action) {
        super(action);
    }

    public static <D,E extends Exception> IPromise<D,E> promise(IAction<D,E> action){
        return new Promise<>(action);
    }

    public static <D,E extends Exception> IPromise<D,E> reject(IAction<D,E> action){
        return new RejectPromise<>(action);
    }

    public static <D,E extends Exception> IPromise<D,E> resolve(IAction<D,E> action){
        return new ResolvePromise<>(action);
    }

    public static <D> IPromise<D,? extends Exception> resovle(D data){
        return new ResolvePromise<>(new ResolveAction<>(data));
    }

    public static <E extends Exception> IPromise<?,E> reject(E ex){
        return new RejectPromise<>(new RejectAction<>(ex));
    }
}
