package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.model.IEntity;
import org.springframework.security.core.GrantedAuthority;

public interface IAuthority extends GrantedAuthority,IEntity {

    Operation getOperation();
    String getTarget();

}
