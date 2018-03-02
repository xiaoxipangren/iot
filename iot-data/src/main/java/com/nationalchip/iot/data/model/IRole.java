package com.nationalchip.iot.data.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IRole extends IEntity,IAuthority {

    String getName();
    String getDescription();
    Collection<IAuthority> getAuthorities();
}
