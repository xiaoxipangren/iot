package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.auth.Role;

public interface RoleRepository extends BaseRepository<Role> {

    Role findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);


}
