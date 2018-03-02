package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.BaseEntity;
import com.nationalchip.iot.data.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:42 PM
 * @Modified:
 */
public abstract class BaseManager<T extends BaseEntity> implements IManager<T,Long> {

    public IRepository<T> getRepository() {
        return repository;
    }

    public void setRepository(IRepository<T> repository) {
        this.repository = repository;
    }

    @Autowired
    private IRepository<T> repository;

    @Override
    public T get(Long id) {
        return repository.getById(id);
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
