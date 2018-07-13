package com.nationalchip.iot.security.authentication;

import java.io.Serializable;
import java.util.Date;

public class AuthenticationDetails implements Serializable{
    private String tenant;
    private boolean once;



    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    private Date expiration;

    public AuthenticationDetails() {
    }

    public AuthenticationDetails(String tenant,Date expiration){
        this.tenant=tenant;
        this.expiration=expiration;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }
}