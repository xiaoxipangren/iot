package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 2:32 PM
 * @Modified:
 */
public abstract class BaseCreupdate<T extends IBuilder<? extends IEntity>> implements ICreupdate<T> {

    private Long id;

    @Override
    public T id(long id) {
        this.id=id;
        return self();
    }

    public Optional<Long> getId(){
        return Optional.ofNullable(id);
    }

    protected T self(){
        return (T)this;
    }

}
