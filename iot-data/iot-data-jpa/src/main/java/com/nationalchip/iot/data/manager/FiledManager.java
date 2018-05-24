package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.IFiledBuilder;
import com.nationalchip.iot.data.model.FiledEntity;
import com.nationalchip.iot.data.model.IFiledEntity;
import com.nationalchip.iot.data.repository.IFiledRepository;
import com.nationalchip.iot.data.repository.IFsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 7:15 PM
 * @Modified:
 */
public abstract class FiledManager<T extends IFiledEntity,E extends T> extends ArchivedManager<T,E> implements IFiledManager<T> {

    @Autowired
    private IFsRepository fsRepository;

    @Override
    public File getBySha1(String sha1) {
        return fsRepository.getBySha1(sha1);
    }

    @Override
    public T create(IBuilder<T> builder) {

        IFiledBuilder<T> filedBuilder=(IFiledBuilder)builder;
        FiledEntity entity = (FiledEntity) filedBuilder.create();
        String hash = fsRepository.create(entity.getContent(),entity.getSha1());
        entity.setSha1(hash);
        T t =(T)entity;
        preCreate(t);
        getRepository().save((E)t);
        postCreate(t);

        return t;
    }

    @Override
    public boolean existsBySha1(String sha1) {
        return repository().existsBySha1(sha1);
    }

    @Override
    public void deleteBySha1(String sha1) {
        repository().deleteBySha1(sha1);
    }

    @Override
    public T findBySha1(String sha1) {
        return repository().findBySha1(sha1);
    }

    private IFiledRepository<T> repository(){
        return (IFiledRepository<T>)getRepository();
    }
}
