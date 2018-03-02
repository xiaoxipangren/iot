package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.User;

public interface TenantRepository extends BaseRepository<User> {

    User findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
