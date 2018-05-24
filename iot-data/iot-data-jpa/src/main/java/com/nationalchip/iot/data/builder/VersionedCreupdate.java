package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IVersionedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:07 PM
 * @Modified:
 */
public abstract class VersionedCreupdate<T extends IVersionedBuilder<? extends IVersionedEntity>> extends ArchivedCreupdate<T> implements IVersionedCreupdate<T>{
    private String version;

    @Override
    public T version(String version) {
        this.version=version;
        return self();
    }

    public Optional<String> getVersion(){
        return Optional.ofNullable(version);
    }
}
