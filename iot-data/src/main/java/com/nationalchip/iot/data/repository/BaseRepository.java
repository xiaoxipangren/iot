package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.BaseEntity;
import com.nationalchip.iot.data.model.TenantAwareEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:36 PM
 * @Modified:
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>  extends IRepository<T> {

}
