package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.INamedBuilder;
import com.nationalchip.iot.data.model.INamedEntity;
import com.nationalchip.iot.data.repository.INamedRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 1:28 PM
 * @Modified:
 */
public abstract class NamedManager<T extends INamedEntity,E extends T> extends BaseManager<T,E> implements INamedManager<T> {

    @Override
    protected T loadEntity(IBuilder<T> builder) {
        T t = super.loadEntity(builder);

        if(t == null && builder instanceof INamedBuilder){
            INamedBuilder<T> namedBuilder = (INamedBuilder<T>)builder;
            if(namedBuilder.getName().isPresent()){
                String name = namedBuilder.getName().get();
                t = ((INamedRepository<E>)getRepository()).findByName(name);
            }
        }

        return t;

    }

    @Override
    public T update(INamedBuilder<T> builder) {
        return super.update(builder);
    }

    @Override
    protected void postCreate(final T t) {

    }

    @Override
    protected void postUpdate(final T t) {

    }

    @Override
    protected void preCreate(T t) {

    }
}
