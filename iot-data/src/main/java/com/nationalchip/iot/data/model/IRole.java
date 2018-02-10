package com.nationalchip.iot.data.model;

import java.util.Collection;

public interface IRole extends IEntity {

    String getName();
    String getDescription();
    Collection<IAuthority> getAuthorities();
}
