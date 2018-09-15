package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.manager.SystemManagerHolder;
import com.nationalchip.iot.data.manager.TenantAwareHolder;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@TenantDiscriminatorColumn(name="tenant",length = 40)
@Multitenant(MultitenantType.SINGLE_TABLE)
public abstract class TenantAwareEntity extends NamedEntity implements ITenantAwareEntity{

    @Column(name = "tenant", nullable = false, insertable = false, updatable = false, length = 40)
    @Size(min = 1, max = 40)
    @NotNull
    private String tenant;

    public TenantAwareEntity(){

    }

    @PrePersist
    void prePersist() {
        final String currentTenant = SystemManagerHolder.getInstance().currentTenant();
        if (currentTenant == null) {
            throw new EntityNotFoundException("Tenant "
                    + TenantAwareHolder.getInstance().getTenantAware().getCurrentTenant()
                    + " does not exists, cannot create entity " + this.getClass() + " with id " + super.getId());
        }
        setTenant(currentTenant.toUpperCase());
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