package com.nationalchip.iot.data.model;

public interface ITenantAwareEntity extends INamedEntity{
    String getTenant();
}
