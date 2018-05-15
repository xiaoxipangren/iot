package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.data.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:42 PM
 * @Modified:
 */

public abstract class BaseManager<T extends IEntity,E extends T> implements IManager<T> {


    @Autowired
    private IRepository<E> repository;

    public BaseManager(){

    }



    @Override
    public T create(IBuilder<T> builder) {
        T t = builder.create();

        preCreate(t);
        t = repository.save((E)t);
        postCreate(t);
        return t;
    }

    protected abstract void preCreate(final T t);

    protected abstract void postCreate(final T t);


    protected T loadEntity(IBuilder<T> builder){
        Optional<Long> id = builder.getId();

        if(id.isPresent()) {
            T t = repository.findById(id.get());

            if (t == null)
                throw new EntityNotFoundException(String.format("%s entity with id %d is not found.", t.getClass().getName(), id));
            return t;
        }
        return null;
    }



    @Override
    public T update(IBuilder<T> builder) {
        T t = loadEntity(builder);
        if(t!=null){
            builder.update(t);
            postUpdate(t);
            return repository.save((E)t);
        }
        return t;
    }

    protected abstract void postUpdate(final T t);


    public  IRepository<E> getRepository() {

        return repository;
    }

    public void setRepository(IRepository<E> repository) {
        this.repository = repository;
    }


    @Override
    public Iterable<T> get(Iterable<Long> ids) {

        return convert(repository.findAll(ids));
    }

    @Override
    public Iterable<T> getAll(Sort sort) {

        return convert(repository.findAll());
    }

    @Override
    public Page<T> getAll(Pageable pageable) {

        return repository.findAll(pageable).map(source -> source);
    }

    @Override
    public boolean exists(Long id) {
        return repository.exists(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void delete(Iterable<T> entities) {
        entities.forEach( t-> repository.delete((E)t));
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public T get(Long id) {
        return repository.findOne(id);
    }

    @Override
    public T create(T t) {
        return repository.save((E)t);
    }

    @Override
    public Iterable<T> getAll() {
        return convert(repository.findAll());
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public T update(T t) {
        return repository.save((E)t);
    }


    protected Iterable<T> convert(Iterable<E> entities){
        List<T> list= new LinkedList<>();

        entities.forEach(e -> list.add(e));
        return list;
    }

}
