package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.data.model.auth.Status;

import java.io.Serializable;

public class AuthenticationDetails implements Serializable{
    private String tenant;
    private Status status;

    public AuthenticationDetails() {
    }

    public AuthenticationDetails(String tenant, Status status){
        this.tenant=tenant;
        this.status=status;
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