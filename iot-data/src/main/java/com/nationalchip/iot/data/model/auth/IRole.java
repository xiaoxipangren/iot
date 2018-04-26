package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.model.IEntity;

import java.util.Collection;

public interface IRole extends IEntity,IAuthority {

    String getName();
    String getDescription();
    Collection<IAuthority> getAuthorities();
}
