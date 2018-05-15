package com.nationalchip.iot.test.promise;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 4:03 PM
 * @Modified:
 */
public abstract class BasePromise<D,E extends Exception> implements IPromise<D,E> {
    private boolean processed=false;
    private List<Consumer<D>> successes = new LinkedList<>();
    private List<Consumer<E>> errors = new LinkedList<>();
    private int status=0;

    private IAction<D,E> action;

    protected BasePromise(IAction<D,E> action){
        this.action=action;
    }

    @Override
    public IPromise then(Consumer<D> success) {
        process();
        successes.add(success);
        return this;
    }

    @Override
    public IPromise error(Consumer<E> error) {
        process();
        errors.add(error);
        return this;
    }

    private void resolve(D data){
        this.status=1;
        processed=true;
        for (Consumer<D> success:successes) {
            success.accept(data);
        }
    }

    private void reject(E exception){
        status=-1;
        processed=true;
        for(Consumer<E> error: errors){
            error.accept(exception);
        }
    }

    private void process(){
        if(!processed){
            this.action.action(this::resolve,this::reject);
        }
    }

}
