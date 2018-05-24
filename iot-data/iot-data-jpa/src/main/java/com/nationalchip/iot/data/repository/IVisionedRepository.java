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
public interface IVisionedRepository<T extends IVersionedEntity> extends IArchiveRepository<T> {

    T findByNameAndVision(String name,String version);
    boolean existsByNameAndVersion(String name,String version);
    void deleteByNameAndVersion(String name,String version);

}
