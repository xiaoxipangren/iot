package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.User;

/**
 * Repository的泛型参数必须为Entity class，而不能是interface
 */
public interface TenantRepository extends BaseRepository<User> {

    IUser findByUsername(String username);
    IUser findByEmail(String email);
    IUser findByPhone(String phone);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
