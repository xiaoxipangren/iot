package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IResource;
import com.nationalchip.iot.data.model.Resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:19 PM
 * @Modified:
 */
public class ResourceBuilder extends FiledCreupdate<IResourceBuilder> implements IResourceBuilder {

    private String guide;

    private String category;

    @Override
    public IResourceBuilder guide(String guide) {
        this.guide=guide;
        return self();
    }

    @Override
    public IResourceBuilder category(String category) {
        this.category=category;
        return self();
    }

    @Override
    public IResource create() {
        Resource resource = new Resource();
        getName().ifPresent(name -> resource.setName(name));
        getFileName().ifPresent(fileName -> resource.setFileName(fileName));
        getDescription().ifPresent(des -> resource.setDescription(des));
        getSha1().ifPresent(sha1 -> resource.setSha1(sha1));
        getVersion().ifPresent(version -> resource.setVersion(version));
        isDeleted().ifPresent(deleted -> resource.setDeleted(deleted));
        getContent().ifPresent(content -> resource.setConent(content));

        if(guide!=null)
            resource.setGuide(guide);
        if(category!=null)
            resource.setCategory(category);

        return resource;
    }

    @Override
    public void update(IResource iResource) {
        Resource resource = (Resource)iResource;
        getName().ifPresent(name -> resource.setName(name));
        getFileName().ifPresent(fileName -> resource.setFileName(fileName));
        getDescription().ifPresent(des -> resource.setDescription(des));
        getSha1().ifPresent(sha1 -> resource.setSha1(sha1));
        getVersion().ifPresent(version -> resource.setVersion(version));
        isDeleted().ifPresent(deleted -> resource.setDeleted(deleted));
        if(guide!=null)
            resource.setGuide(guide);
        if(category!=null)
            resource.setCategory(category);
    }
}
