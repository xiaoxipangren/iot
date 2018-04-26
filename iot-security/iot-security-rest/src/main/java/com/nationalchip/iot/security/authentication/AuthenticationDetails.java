package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.data.model.auth.Status;

import java.io.Serializable;
import java.util.Date;

public class AuthenticationDetails implements Serializable{
    private String tenant;
    private Status status;

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    private Date expiration;

    public AuthenticationDetails() {
    }

    public AuthenticationDetails(String tenant, Status status,Date expiration){
        this.tenant=tenant;
        this.status=status;
        this.expiration=expiration;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}