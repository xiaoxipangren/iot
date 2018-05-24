package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:06 PM
 * @Modified:
 */
@Repository
public interface ResourceRepository extends IFiledRepository<Resource> {

    Iterable<Resource> findByCategory(String category);
}
