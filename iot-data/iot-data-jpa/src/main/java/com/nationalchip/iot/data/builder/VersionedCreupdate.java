package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IVersionedEntity;
import com.nationalchip.iot.data.model.VersionedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:07 PM
 * @Modified:
 */
public abstract class VersionedCreupdate<T extends IVersionedBuilder<E>, E extends IVersionedEntity> extends ArchivedCreupdate<T,E> implements IVersionedCreupdate<T,E>{
    private String version;

    @Override
    public T version(String version) {
        this.version=version;
        return self();
    }

    public Optional<String> getVersion(){
        return Optional.ofNullable(version);
    }

    @Override
    protected void apply(E entity) {
        super.apply(entity);

        this.<VersionedEntity>tryCast(entity).ifPresent(e->getVersion().ifPresent(v-> e.setVersion(v)));

    }
}
