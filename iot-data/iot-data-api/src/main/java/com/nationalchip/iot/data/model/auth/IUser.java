package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.model.IEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

public interface IUser extends UserDetails,IEntity {

    Date getLastLogin();
    String getPhone();
    String getEmail();
    String getTenant();
    Status getStatus();
    String getAvatar();
    Set<IRole> getRoles();
    boolean addRole(IRole role);
    boolean removeRole(IRole role);
    boolean isInRole(IRole role);
}
