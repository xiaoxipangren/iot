package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IArchivedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:46 PM
 * @Modified:
 */
public interface IArchivedManager<T extends IArchivedEntity> extends INamedManager<T> {
    void softDelete(Long id);
    void softDelete(Iterable<T> entities);
    void softDeleteAll(Iterable<Long> ids);
    void softDeleteAll();


    Iterable<T> findByTag(String tag);
    Page<T> findByTag(String tag, Pageable pageable);
    void deleteByTag(String tag);
    Long countByTag(String tag);
    Iterable<String> getTags();

}
