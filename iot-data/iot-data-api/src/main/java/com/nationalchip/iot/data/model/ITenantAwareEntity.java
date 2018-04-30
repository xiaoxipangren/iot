package com.nationalchip.iot.data.model;

public interface ITenantAwareEntity extends IEntity{
    String getTenant();
}
