package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IResource;
import com.nationalchip.iot.data.model.Resource;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.data.repository.ResourceRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:33 PM
 * @Modified:
 */
public class ResourceManager extends ArchiveManager<IResource,Resource> implements IResourceManager {
    @Override
    public boolean existsBySha1(String sha1) {
        return repository().existsBySha1(sha1);
    }

    @Override
    public IResource findBySha1(String sha1) {
        return repository().findBySha1(sha1);
    }

    @Override
    public void deleteBySha1(String sha1) {
        repository().deleteBySha1(sha1);
    }


    private ResourceRepository repository(){
        IRepository<Resource> repository = getRepository();

        if(repository instanceof ResourceRepository){
            return (ResourceRepository) repository;
        }
        else
            throw new NotImplementedException();
    }

}
