package com.nationalchip.iot.data.manager;

import com.google.common.collect.Lists;
import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.data.specification.SpecificationParser;
import com.nationalchip.iot.helper.TypeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:42 PM
 * @Modified:
 */

public abstract class BaseManager<T extends IEntity,E extends T> implements IManager<T> {


    @Autowired
    private SpecificationParser specificationParser;



    @Autowired
    private IRepository<E> repository;

    public BaseManager(){

    }


    @Override
    public T create(IBuilder<T> builder) {

        checkExisted(builder,true);

        T t = builder.create();

        preCreate(t);

        t = repository.save((E)t);

        postCreate(t);

        return t;
    }

    protected void checkExisted(IBuilder<T> builder,boolean throwIfExisted){
        Optional<Long> id = builder.getId();

        if(id.isPresent()){
            Long idL = id.get();
            boolean existed=exists(idL);
            if(existed && throwIfExisted){
                throw existsException("id",idL+"", getGenericTypeName(1));
            }

            if(!existed && !throwIfExisted){
                throw notFoundException("id",idL+"", getGenericTypeName(1));
            }
        }



    }

    protected  void preCreate(final T t){}

    protected  void postCreate(final T t){}


    protected T loadEntity(IBuilder<T> builder){
        checkExisted(builder,false);
        if(builder.getId().isPresent()){
            return repository.findById(builder.getId().get());
        }

        return null;

    }

    protected void preUpdate(final T t){}


    @Override
    public T update(IBuilder<T> builder) {
        T t = loadEntity(builder);
        if(t!=null){
            builder.update(t);
            preUpdate(t);
            t = repository.save((E)t);
            postUpdate(t);
            return t;
        }
        return t;
    }

    protected void postUpdate(final T t){};


    public  IRepository<E> getRepository() {

        return  repository;
    }

    public void setRepository(IRepository<E> repository) {
        this.repository = repository;
    }


    @Override
    public Iterable<T> find(Iterable<Long> ids) {

        return convert(repository.findAll(ids));
    }

    @Override
    public Iterable<T> findAll(Sort sort) {

        return convert(repository.findAll(sort));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {

        return convert(repository.findAll(pageable),pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable, String condition) {

        Specification<E> specification  = specificationParser.parse(condition);
        Page<E> page = getRepository().findAll(specification,pageable);

        return convert(page,pageable);
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
    public long count(String condition) {

        if(condition==null || condition.isEmpty())
            return count();

        Specification<E> specification  = specificationParser.parse(condition);
        return repository.count(specification);
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
    public T findOne(Long id) {

        T t = repository.findOne(id);
        if(t == null){
            throw new EntityNotFoundException();
        }
        return t;
    }

    @Override
    public T create(T t) {
        return repository.save((E)t);
    }

    @Override
    public Iterable<T> findAll() {
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

    protected Page<T> convert(final Page<E> page, final Pageable pageable) {
        return new PageImpl<>(Collections.unmodifiableList(page.getContent()), pageable, page.getTotalElements());
    }

    protected List<T> toList(Iterable<T> iterable){
        List<T> list = Lists.newArrayList();
        iterable.forEach(i -> list.add(i));
        return list;
    }

    protected EntityExistsException existsException(String filed, String identity,String type){
        return new EntityExistsException(String.format("%s为%s的%s实体已存在",filed,identity,type));
    }
    protected EntityNotFoundException notFoundException(String filed, String identity,String type){
        return new EntityNotFoundException(String.format("%s为%s的%s实体不存在",filed,identity,type));
    }

    protected String getGenericTypeName(int index){
        return TypeHelper.getGenericTypes(getClass())[index].getTypeName();
    }

}
