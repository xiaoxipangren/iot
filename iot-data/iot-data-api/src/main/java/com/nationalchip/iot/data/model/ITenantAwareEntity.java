package com.nationalchip.iot.data.model;

/**
 * 多租户实体的父级接口，对于由消费者和开发者关联的资源都应是
 * 该接口的子接口，以保证通过tenant字段进行资源隔离
 */

public interface ITenantAwareEntity extends INamedEntity{
    String getTenant();
}
