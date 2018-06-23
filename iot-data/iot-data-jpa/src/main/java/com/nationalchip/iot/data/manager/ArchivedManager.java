package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.ArchivedEntity;
import com.nationalchip.iot.data.model.IArchivedEntity;
import com.nationalchip.iot.data.repository.IArchiveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:40 PM
 * @Modified:
 */
public abstract class ArchivedManager<T extends IArchivedEntity,E extends T> extends NamedManager<T,E> implements IArchivedManager<T> {
    @Override
    public void softDelete(Long id) {
        T entity = getRepository().findById(id);
        if(entity instanceof ArchivedEntity){
            ArchivedEntity archiveEntity =(ArchivedEntity) entity;
            delete(archiveEntity);
        }

    }

    @Override
    public void softDelete(Iterable<T> entities) {
        entities.forEach(t -> delete((ArchivedEntity)t));
    }

    @Override
    public void softDeleteAll(Iterable<Long> ids) {
        ids.forEach(id -> softDelete(id));
    }

    @Override
    public void softDeleteAll() {
        softDelete(convert(getRepository().findAll()));
    }

    private void delete(ArchivedEntity entity){
        entity.setDeleted(true);
        getRepository().save((E)entity);
    }


    @Override
    public Iterable<T> findByTag(String tag) {
        return convert(getRepository().findByTag(tag));
    }

    @Override
    public Page<T> findByTag(String tag, Pageable pageable) {
        Page<E> page = getRepository().findByTag(tag,pageable);

        return new PageImpl((List<T>)convert(page.getContent()),pageable,page.getTotalElements());

    }

    @Override
    public void deleteByTag(String tag) {
        getRepository().deleteByTag(tag);
    }

    @Override
    public Long countByTag(String tag) {
        return getRepository().countByTag(tag);
    }

    @Override
    public Iterable<String> getTags() {
        return toList(findAll()).stream().map(a -> a.getTag()).distinct().collect(Collectors.toList());
    }


    @Override
    public IArchiveRepository<E> getRepository(){
        return (IArchiveRepository<E>) super.getRepository();
    }
}
