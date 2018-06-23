package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IVersionedEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:29 PM
 * @Modified:
 */
@NoRepositoryBean
public interface IVersionedRepository<T extends IVersionedEntity> extends IArchiveRepository<T> {

    T findByNameAndVersion(String name,String version);
    boolean existsByNameAndVersion(String name,String version);
    void deleteByNameAndVersion(String name,String version);

}
