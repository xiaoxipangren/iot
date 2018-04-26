package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.BaseEntity;
import com.nationalchip.iot.data.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:42 PM
 * @Modified:
 */
public abstract class BaseManager<T extends BaseEntity> implements IManager<T> {

    @Autowired
    private IRepository<T> repository;


    public IRepository<T> getRepository() {
        return repository;
    }

    public void setRepository(IRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public Iterable<T> get(Iterable<Long> ids) {
        return repository.findAll();
    }

    @Override
    public Iterable<T> getAll(Sort sort) {
        return repository.findAll();
    }

    @Override
    public Page<T> getAll(Pageable pageable) {
        return repository.findAll(pageable);
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
        repository.delete(entities);
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
        return repository.save(t);
    }

    @Override
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }
}
