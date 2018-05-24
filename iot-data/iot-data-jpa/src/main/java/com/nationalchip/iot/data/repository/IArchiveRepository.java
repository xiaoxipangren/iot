package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IArchivedEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:08 PM
 * @Modified:
 */
@NoRepositoryBean
public interface IArchiveRepository<T extends IArchivedEntity> extends INamedRepository<T> {

}
