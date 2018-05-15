package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.model.INamedEntity;

import java.util.Collection;

public interface IRole extends INamedEntity,IAuthority {

    String getName();
    String getDescription();
    Collection<IAuthority> getAuthorities();
}
