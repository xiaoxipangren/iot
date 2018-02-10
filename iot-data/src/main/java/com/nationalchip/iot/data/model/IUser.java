package com.nationalchip.iot.data.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface IUser extends UserDetails,IEntity {

    Date getLastLogin();
    String getPhone();
    String getEmail();
    String getTenant();

}
