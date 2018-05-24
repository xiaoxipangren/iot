package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.ArchiveEntity;
import com.nationalchip.iot.data.model.IArchivedEntity;
import com.nationalchip.iot.data.repository.IArchiveRepository;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:40 PM
 * @Modified:
 */
public abstract class ArchiveManager<T extends IArchivedEntity,E extends T> extends NamedManager<T,E> implements IArchivedManager<T> {
    @Override
    public void softDelete(Long id) {
        T entity = ((IArchiveRepository<T>)getRepository()).findById(id);
        if(entity instanceof ArchiveEntity){
            ArchiveEntity archiveEntity =(ArchiveEntity) entity;
            delete(archiveEntity);
        }

    }

    @Override
    public void softDelete(Iterable<T> entities) {
        entities.forEach(t -> delete((ArchiveEntity)t));
    }

    @Override
    public void softDeleteAll() {
        softDelete(((IArchiveRepository<T>)getRepository()).findAll());
    }

    private void delete(ArchiveEntity entity){
        entity.setDeleted(true);
        getRepository().save((E)entity);
    }


}
