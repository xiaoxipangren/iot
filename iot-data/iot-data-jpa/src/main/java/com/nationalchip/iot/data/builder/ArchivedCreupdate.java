package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IArchivedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:03 PM
 * @Modified:
 */
public abstract class ArchivedCreupdate<T extends IArchivedBuilder<? extends IArchivedEntity>> extends NamedCreupdate<T> implements IArchivedCreupdate<T> {

    private boolean deleted;

    @Override
    public T deleted(boolean deleted) {
        this.deleted=deleted;
        return self();
    }

    public Optional<Boolean> isDeleted(){
        return Optional.ofNullable(deleted);
    }
}
