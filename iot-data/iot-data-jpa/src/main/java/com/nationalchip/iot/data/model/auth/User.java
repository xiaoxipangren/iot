package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 抽象的用户类
 * 共有四种不同的用户和系统发生交互
 * Admin:系统管理员
 * Developer:厂商用户
 * Consumer:消费者(app用户)
 *
 */

@MappedSuperclass
@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends NamedEntity implements IUser {

    @Column(name = "password")
    private  String password;



    @Comment("用户头像")
    @Column(name = "avatar")
    private String avatar;

    @CascadeOnDelete
    @ManyToMany(targetEntity = Authority.class)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name="user_id",nullable = false,updatable = false)},
            inverseJoinColumns = {@JoinColumn(name="authority_id",nullable = false,updatable = false)})
    private Set<IAuthority> authorities;


    @CascadeOnDelete
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name="user_id",nullable = false,updatable = false)},
            inverseJoinColumns = {@JoinColumn(name="role_id",nullable = false,updatable = false)})
    private Set<IRole> roles;

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Comment("账户状态")
    @Column(name="status")
    private Status status;


    public User(){
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
        this.status=Status.REGISTERED;
    }


    public User(String username, String password){
        this();
        setName(username);
        this.password=password;
    }



    @Column(name = "account_nonexpired")
    @Comment("账户是否过期")
    private  boolean accountNonExpired;

    @Comment("账户是否锁定")
    @Column(name="account_nonlocked")
    private  boolean accountNonLocked;

    @Comment("密码是否过期")
    @Column(name = "credentials_nonexpired")
    private  boolean credentialsNonExpired;

    @Comment("账户是否启用")
    @Column(name = "enabled")
    private  boolean enabled;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Comment("电话")
    @Column(name = "phone")
    private String phone;

    @Comment("邮箱")
    @Column(name = "email")
    private String email;

    @Comment("最后登录")
    @Column(name = "last_login")
    private Date lastLogin;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return getName();
    }


    @Override
    public Set<IAuthority> getAuthorities() {
        Set<IAuthority> authorities = new HashSet<>(this.authorities);

        if(this.roles!=null){
            this.roles.stream()
                    .forEach(
                            r -> {
                                authorities.add(r);
                                r.getAuthorities().stream().forEach( a -> authorities.add(a));

                            }
                    );
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public Date getLastLogin() {
        return this.lastLogin;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public String getEmail() {
        return this.email;
    }



    public boolean addAuthority(final IAuthority authority){
        if(this.authorities == null){
            authorities = new HashSet<>();
        }

        return authorities.add(authority);
    }


    public boolean removeAuthority(final IAuthority authority){
        if(authorities == null)
            return  false;
        return authorities.remove(authority);
    }


    public Set<IAuthority> getAssignedAuthorities(){
        if(this.authorities == null)
            return Collections.emptySet();

        return Collections.unmodifiableSet(this.authorities);
    }


    public boolean addRole(final IRole role){
        if(this.roles == null)
            this.roles = new HashSet<>();

        return this.roles.add(role);
    }


    public boolean removeRole(final IRole role){
        if(this.roles == null)
            return false;

        return this.roles.remove(role);
    }

    @Override
    public boolean isInRole(IRole role) {
        if(role == null || this.roles==null)
            return false;

        return this.roles.contains(role);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAuthorities(final Set<IAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setRoles(final Set<IRole> roles) {
        this.roles = roles;
    }

    public Set<IRole> getRoles() {

        if(this.roles == null)
            return Collections.emptySet();
        return Collections.unmodifiableSet(roles);
    }



    public static IUser SystemUser(String tenant){
        IUser user = new User(com.nationalchip.iot.security.authority.Authority.SYSTEM,"***");
        return user;
    }
}

