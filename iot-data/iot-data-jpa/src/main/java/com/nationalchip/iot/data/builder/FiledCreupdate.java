package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IFiledEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:11 PM
 * @Modified:
 */
public abstract class FiledCreupdate<T extends IFiledBuilder<? extends IFiledEntity>>  extends VersionedCreupdate<T> implements IFiledCreupdate<T> {
    private String sha1;
    private String fileName;
    private Long size;

    @Override
    public T sha1(String sha1) {
        this.sha1=sha1;
        return self();
    }

    @Override
    public T fileName(String fileName) {
        this.fileName=fileName;
        return self();
    }

    @Override
    public T size(Long size) {
        this.size=size;
        return self();
    }


    public Optional<String> getFileName(){
        return Optional.ofNullable(fileName);
    }

    public Optional<String> getSha1(){
        return Optional.ofNullable(sha1);
    }
    public Optional<Long> getSize(){
        return Optional.ofNullable(size);
    }
}
