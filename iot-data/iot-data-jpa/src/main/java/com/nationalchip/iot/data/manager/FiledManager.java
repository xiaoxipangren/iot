package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.FiledEntity;
import com.nationalchip.iot.data.model.IFiledEntity;
import com.nationalchip.iot.data.model.NamedEntity;
import com.nationalchip.iot.data.repository.IFiledRepository;
import com.nationalchip.iot.data.repository.IFsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    public T findOne(Long id) {
        T t = super.findOne(id);
        FiledEntity entity = (FiledEntity) t;
        try {
            entity.setStream(new FileInputStream(fsRepository.getFile(t.getSha1())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (T)entity;
    }

    @Override
    public File getFile(String sha1) {
        return fsRepository.getFile(sha1);
    }


    @Override
    protected void preCreate(T t) {
        super.preCreate(t);
        saveFile(t);

    }


    private void saveFile (T t){
        FiledEntity entity = (FiledEntity) t;
        if(entity.getStream()!=null){
            String hash = fsRepository.create(entity.getStream(),null);
            entity.setSha1(hash);
        }
    }


    @Override
    protected void preUpdate(T t) {
        super.preUpdate(t);
        saveFile(t);
    }

    @Override
    public boolean existsBySha1(String sha1) {
        return repository().existsBySha1(sha1) && fsRepository.existsBySha1(sha1);
    }

    @Override
    public void deleteBySha1(String sha1) {
        repository().deleteBySha1(sha1);
        fsRepository.deleteBySha1(sha1);
    }

    @Override
    public T findBySha1(String sha1) {
        return repository().findBySha1(sha1);
    }

    private IFiledRepository<E> repository(){
        return (IFiledRepository<E>)getRepository();
    }
}
