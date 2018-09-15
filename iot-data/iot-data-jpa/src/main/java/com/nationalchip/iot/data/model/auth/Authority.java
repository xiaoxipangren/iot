package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.BaseEntity;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 权限类，权限由动作opration(CRUD的枚举值)和
 * 动作作用的目标target(所有的实体名)组成。
 * 因此权限的数量是是所有的实体个数*4
 */

@MappedSuperclass
@Entity
@Table(name="authority")
public class Authority extends BaseEntity implements IAuthority {

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    @Comment("权限动作")
    @NotNull
    private Operation operation;

    @Column(name = "target")
    @Comment("权限目标")
    @NotNull
    private String target;



    @CascadeOnDelete
    @ManyToMany(mappedBy = "authorities",targetEntity = User.class,fetch = FetchType.LAZY)
    private Set<IUser> users;


    @CascadeOnDelete
    @ManyToMany(mappedBy = "authorities",targetEntity = Role.class,fetch = FetchType.LAZY)
    private Set<Role> roles;

    public Authority(){

    }

    public Authority(Operation operation, String target){
        this.operation= operation;
        this.target=target;
    }


    public Authority(String action,String target){
        this.operation = Operation.valueOf(action);
        this.target=target;
    }


    public Operation getOperation() {
        return operation;
    }

    public void setAction(Operation operation) {
        this.operation = operation;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return String.join(com.nationalchip.iot.security.authority.Authority.SEPARATOR,operation.getName().toUpperCase(),target.toUpperCase());
    }

    @Override
    public boolean equals(Object o){

        if(this==o)
            return true;

        if(o==null || !(o instanceof Authority))
            return false;

        Authority au = (Authority)o;

        if(operation==au.getOperation() && target.equalsIgnoreCase(au.getTarget()))
            return true;

        return false;
    }

    @Override
    public String getAuthority() {
        return toString();
    }
}
