package com.nationalchip.iot.data.model;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 权限类，权限由动作action(CRUD的枚举值)和
 * 动作作用的目标target(所有的实体名)组成。
 * 因此权限的数量是是所有的实体个数*4
 */

@MappedSuperclass
@Entity
@Table(name="authority")
public class Authority extends BaseEntity implements IAuthority {

    private static final String SEPERATOR="_";

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "COMMENT '权限动作'")
    @NotNull
    private Action action;

    @Column(columnDefinition = "COMMENT '权限目标'")
    @NotNull
    private String target;



    @CascadeOnDelete
    @ManyToMany(mappedBy = "authorities",targetEntity = User.class,fetch = FetchType.LAZY)
    private Set<User> users;


    @CascadeOnDelete
    @ManyToMany(mappedBy = "authorities",targetEntity = Role.class,fetch = FetchType.LAZY)
    private Set<Role> roles;

    public Authority(){

    }

    public Authority(Action action,String target){
        this.action=action;
        this.target=target;
    }


    public Authority(String action,String target){
        this.action = Action.valueOf(action);
        this.target=target;
    }


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return String.join(SEPERATOR,action.toString().toUpperCase(),target.toLowerCase());
    }

    @Override
    public boolean equals(Object o){

        if(this==o)
            return true;

        if(o==null || !(o instanceof Authority))
            return false;

        Authority au = (Authority)o;

        if(action==au.getAction() && target.equalsIgnoreCase(au.getTarget()))
            return true;

        return false;
    }

    @Override
    public String getAuthority() {
        return toString();
    }
}
