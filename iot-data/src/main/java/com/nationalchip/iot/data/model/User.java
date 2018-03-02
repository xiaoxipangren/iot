package com.nationalchip.iot.data.model;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Table(name="user")
@Entity
public class User extends BaseEntity implements IUser {

    @Column(name = "password")
    private  String password;

    @Column(name = "username",unique = true)

    private  String username;



    @Column(name = "tenant",unique = true)
    private String tenant;


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


    public User(){
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
    }


    public User(String username,String password){
        this();
        this.username=username;
        this.password=password;
        this.tenant=username;
    }


    @Column(name = "account_nonexpired")
    private  boolean accountNonExpired;

    @Column(name="account_nonlocked")
    private  boolean accountNonLocked;

    @Column(name = "credentials_nonexpired")
    private  boolean credentialsNonExpired;

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

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

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
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String getTenant() {
        return tenant;
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

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
