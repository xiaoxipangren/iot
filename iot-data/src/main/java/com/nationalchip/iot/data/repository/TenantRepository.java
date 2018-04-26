package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.auth.User;

public interface TenantRepository extends BaseRepository<User> {

    User findByUsername(String username);

    User findByEmail(String email);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
