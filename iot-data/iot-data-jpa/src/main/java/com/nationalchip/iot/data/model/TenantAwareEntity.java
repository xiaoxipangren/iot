package com.nationalchip.iot.data.model;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * FIXME:
 * 利用prepersist添加tenant信息
 */

@MappedSuperclass
@TenantDiscriminatorColumn(name="tenant",length = 40)
@Multitenant(MultitenantType.SINGLE_TABLE)
public abstract class TenantAwareEntity extends BaseEntity implements ITenantAwareEntity{

    @Column(name = "tenant", nullable = false, insertable = false, updatable = false, length = 40)
    @Size(min = 1, max = 40)
    @NotNull
    private String tenant;

    public TenantAwareEntity(){

    }

    public TenantAwareEntity(String tenant){
        this.tenant=tenant;
    }

    public void setTenant(String tenant){
        this.tenant=tenant;
    }

    @Override
    public String getTenant() {
        return tenant;
    }
}