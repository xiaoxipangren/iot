package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 10:56 AM
 * @Modified:
 */
public abstract class BaseBuilder<T extends ICreupdate> implements IBuilder<T> {
    protected long id;


    @Override
    public T id(long id) {
        this.id=id;
        return (T)this;
    }

    @Override
    public long getId() {
        return id;
    }
}
