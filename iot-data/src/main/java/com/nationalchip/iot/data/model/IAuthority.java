package com.nationalchip.iot.data.model;

import org.springframework.security.core.GrantedAuthority;

public interface IAuthority extends GrantedAuthority,IEntity {

    Action getAction();
    String getTarget();

}
