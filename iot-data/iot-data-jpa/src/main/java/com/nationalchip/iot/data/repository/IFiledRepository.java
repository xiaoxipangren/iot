package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IFiledEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:32 PM
 * @Modified:
 */
@NoRepositoryBean
public interface IFiledRepository<T extends IFiledEntity> extends IVisionedRepository<T> {

    T findBySha1(String sha1);
    boolean existsBySha1(String sha1);
    void deleteBySha1(String sha1);

}
