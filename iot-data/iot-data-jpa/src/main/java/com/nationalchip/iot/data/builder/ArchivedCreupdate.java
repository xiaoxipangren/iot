package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.ArchivedEntity;
import com.nationalchip.iot.data.model.IArchivedEntity;
import org.eclipse.persistence.jpa.Archive;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:03 PM
 * @Modified:
 */
public abstract class ArchivedCreupdate<T extends IArchivedBuilder<E>,E extends IArchivedEntity> extends NamedCreupdate<T,E> implements IArchivedCreupdate<T,E> {

    private boolean deleted;
    private String tag;

    @Override
    public T deleted(boolean deleted) {
        this.deleted=deleted;
        return self();
    }

    public Optional<Boolean> isDeleted(){
        return Optional.ofNullable(deleted);
    }

    @Override
    public T tag(String tag) {
        this.tag=tag;
        return self();
    }

    public Optional<String> getTag() {
        return Optional.ofNullable(tag);
    }

    @Override
    protected void apply(E entity) {
        super.apply(entity);

        this.<ArchivedEntity>tryCast(entity).ifPresent(
                e->{
                    isDeleted().ifPresent(d -> e.setDeleted(d));
                    getTag().ifPresent(t -> e.setTag(t));
                }
        );
    }
}
