package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.data.model.INamedEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.InputStream;
import java.util.Date;
import java.util.Set;

public interface IUser extends UserDetails,INamedEntity {
    Date getLastLogin();
    String getPhone();
    String getEmail();
    Status getStatus();
    String getAvatar();
    Set<IRole> getRoles();
    boolean isMatch(String client);
    boolean addRole(IRole role);
    boolean removeRole(IRole role);
    boolean isInRole(IRole role);
}
