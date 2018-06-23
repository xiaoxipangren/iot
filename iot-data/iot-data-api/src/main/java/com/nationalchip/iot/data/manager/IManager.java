package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.model.IEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:39 PM
 * @Modified:
 */
public interface IManager<T extends IEntity> {
    T findOne(Long id);
    Iterable<T> findAll();
    Iterable<T> find(Iterable<Long> ids);
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);

    boolean exists(Long id);

    T update(T t);
    T create(T t);

    T create(IBuilder<T> builder);
    T update(IBuilder<T> builder);


    long count();

    void delete(Long id);
    void delete(Iterable<T> entities);
    void deleteAll();


}
