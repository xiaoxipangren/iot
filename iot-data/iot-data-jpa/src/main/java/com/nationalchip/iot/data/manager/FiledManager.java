package com.nationalchip.iot.data.manager;

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
            String hash = fsRepository.create(entity.getStream(),entity.getSha1());
            entity.setSha1(hash);
        }
    }


    @Override
    protected void postUpdate(T t) {
        super.postUpdate(t);
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
