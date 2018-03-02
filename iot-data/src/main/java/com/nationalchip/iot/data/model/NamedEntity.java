package com.nationalchip.iot.data.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity extends TenantAwareEntity implements INamedEntity {

    @Column(name="name",unique = true)
    private String name;


    @Column(name="description")
    private String description;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName(){
        return this.name;
    }
}
