package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.INamedBuilder;
import com.nationalchip.iot.data.model.INamedEntity;
import com.nationalchip.iot.data.repository.INamedRepository;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.helper.TypeHelper;

import javax.persistence.EntityExistsException;
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
    protected boolean checkExisted(IBuilder<T> builder, boolean throwIfExisted) {
        boolean existed = super.checkExisted(builder, throwIfExisted);

        if(!existed && builder instanceof INamedBuilder){
            INamedBuilder<T> namedBuilder = (INamedBuilder<T>)builder;

            Optional<String> optionalName = namedBuilder.getName();
            if(optionalName.isPresent()){
                String name = optionalName.get();
                existed = existsByName(name);

                if(existed && throwIfExisted){
                    throw existsException("名称",name, getGenericTypeName(1));
                }

                if(!existed && !throwIfExisted){
                    throw notFoundException("名称",name,getGenericTypeName(1));
                }

                return existed;
            }
        }
        return false;
    }

    @Override
    protected T loadEntity(IBuilder<T> builder) {
        T t = super.loadEntity(builder);

        if(t == null && builder instanceof INamedBuilder){

            INamedBuilder<T> namedBuilder = (INamedBuilder<T>)builder;
            if(namedBuilder.getName().isPresent()){
                return getRepository().findByName(namedBuilder.getName().get());
            }
        }

        return t;

    }


    @Override
    public boolean existsByName(String name) {

        return getRepository().existsByName(name);
    }

    @Override
    public void deleteByName(String name) {
        getRepository().deleteByName(name);
    }

    @Override
    public T findByName(String name) {
        return getRepository().findByName(name);
    }

    @Override
    public INamedRepository<E> getRepository() {
        return (INamedRepository<E>) super.getRepository();
    }


}
