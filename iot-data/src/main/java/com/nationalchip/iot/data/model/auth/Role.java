package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.configuration.DataConstant;
import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.BaseEntity;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.security.access.prepost.PreAuthorize;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@MappedSuperclass
@Table(name = "role")
public class Role extends BaseEntity implements IRole {

    @NotNull
    @Column(name="name")
    @Comment("角色名")
    private String name;

    @Comment("角色描述")
    @Column(name="description")
    private String description;


    @CascadeOnDelete
    @ManyToMany(targetEntity = Authority.class)
    @JoinTable(name = "role_authority",
    joinColumns = {@JoinColumn(name = "role_id",nullable = false,updatable = false)},
    inverseJoinColumns = {@JoinColumn(name = "authority_id",nullable = false,updatable = false)})
    private Set<IAuthority> authorities;


    @CascadeOnDelete
    @ManyToMany(mappedBy = "roles",targetEntity = User.class,fetch = FetchType.LAZY)
    private Set<IUser> users;

    public Role() {

    }

    public Role(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<IAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<IAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<IUser> getUsers() {
        return users;
    }

    public void setUsers(Set<IUser> users) {
        this.users = users;
    }


    @Override
    public String getAuthority() {
        return DataConstant.ROLE_PREFIX+name.toUpperCase();
    }

    @Override
    public String toString(){
        return DataConstant.ROLE_PREFIX+name.toUpperCase();
    }

    @Override
    public Operation getOperation() {
        throw new NotImplementedException();
    }

    @Override
    public String getTarget() {
        throw  new NotImplementedException();
    }


    public static Role SystemRole(){
        return new Role(DataConstant.SYSTEM_ROLE);
    }
}
