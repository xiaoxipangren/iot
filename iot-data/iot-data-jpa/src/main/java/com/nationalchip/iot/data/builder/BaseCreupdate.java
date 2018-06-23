package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.BaseEntity;
import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.data.model.NamedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 2:32 PM
 * @Modified:
 */
public abstract class BaseCreupdate<T extends IBuilder<E>,E extends IEntity> implements ICreupdate<T,E> {

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


    protected void apply(E entity){
        this.<BaseEntity>tryCast(entity).ifPresent(e -> getId().ifPresent(id -> e.setId(id)));
    }


    protected <V> Optional<V> tryCast(E entity){
        V v=null;

        try {
            v = (V) entity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(v);
    }

    protected abstract E newInstance();


    @Override
    public E create() {
        E e = newInstance();
        apply(e);
        return e;
    }

    @Override
    public void update(E e) {
        apply(e);
    }
}
