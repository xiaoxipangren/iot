package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IResource;
import com.nationalchip.iot.data.model.Resource;
import com.nationalchip.iot.data.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:33 PM
 * @Modified:
 */

@Component
public class ResourceManager extends FiledManager<IResource,Resource> implements IResourceManager {


    @Override
    public Iterable<IResource> findByCategory(String catetory) {

        return convert(repository().findByCategory(catetory));
    }

    private ResourceRepository repository(){
        if(getRepository() instanceof ResourceRepository){
            return (ResourceRepository)getRepository();
        }
        throw new NotImplementedException();
    }

}
