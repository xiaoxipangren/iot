package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.auth.Authority;
import com.nationalchip.iot.data.model.auth.Operation;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/18/18 1:06 PM
 * @Modified:
 */
public interface AuthorityRepository extends IRepository<Authority>{
    boolean existsByOperationAndTarget(Operation operation,String target);
    Authority findByOperationAndTarget(Operation operation,String target);
    void deleteByOperationAndTarget(Operation operation,String target);
}
