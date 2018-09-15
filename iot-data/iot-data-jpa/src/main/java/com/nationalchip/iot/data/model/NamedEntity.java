package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class NamedEntity extends BaseEntity implements INamedEntity {

    public NamedEntity(){

    }

    public NamedEntity(String name){
        this.name=name;
    }


    @Column(name="name",unique = true)
    @Comment("名称")
    @NotNull
    private String name;


    @Column(name="description")
    @Comment("描述信息")
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
