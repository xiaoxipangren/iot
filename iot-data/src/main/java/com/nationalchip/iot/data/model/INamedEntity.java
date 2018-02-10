package com.nationalchip.iot.data.model;

public interface INamedEntity extends ITenantAwareEntity {
    String getName();
    String getDescription();
}
