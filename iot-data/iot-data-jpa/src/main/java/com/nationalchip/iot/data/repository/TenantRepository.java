package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.User;
import org.springframework.stereotype.Repository;

/**
 * Repository的泛型参数必须为Entity class，而不能是interface
 */
@Repository
public interface TenantRepository extends INamedRepository<User> {
    IUser findByEmail(String email);
    IUser findByPhone(String phone);

    void deleteByName(String username);

    boolean existsByName(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
