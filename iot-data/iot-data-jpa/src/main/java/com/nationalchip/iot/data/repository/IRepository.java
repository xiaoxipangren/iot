package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:36 PM
 * @Modified:
 */
@NoRepositoryBean
public interface IRepository<T extends IEntity> extends PagingAndSortingRepository<T,Long> {
    T findById(Long id);
}
