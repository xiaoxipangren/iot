package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.*;
import com.nationalchip.iot.data.repository.RoleRepository;
import com.nationalchip.iot.data.repository.TenantRepository;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.eclipse.persistence.internal.libraries.antlr.runtime.misc.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Component
@Transactional(readOnly = true)
public class UserManager extends BaseManager<User> implements UserDetailsManager{

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ITenantAware tenantAware;


    @PreAuthorize("hasRole('SYSTEM') or hasAuthority('CREATE_USER')")
    @Override
    public void createUser(UserDetails userDetails) {
        User user = (User) userDetails;

        if(userExists(user.getUsername())){
            throw new EntityExistsException(String.format("用户%s已存在",user.getUsername()));
        }
        if(userExistsByEmail(user.getEmail())){
            throw new EntityExistsException(String.format("邮箱%s已绑定",user.getEmail()));
        }
        if(userExistsByPhone(user.getPhone())){
            throw new EntityExistsException(String.format("手机%s已绑定",user.getPhone()));
        }

        user.setStatus(Status.REGISTERED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        create(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        update((User)user);
    }

    @Override
    public void deleteUser(String username) {
        ((TenantRepository)getRepository()).deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String username = tenantAware.getCurrentTenant();
        User user = loadUser(username);
        if(user.getPassword().equals(passwordEncoder.encode(oldPassword))){
            user.setPassword(passwordEncoder.encode(newPassword));
            update(user);
        }
        else {
            throw new BadCredentialsException("用户密码错误");
        }
    }

    public void resetPassword(String username,String password){
        User user = loadUser(username);
        user.setPassword(passwordEncoder.encode(password));
        update(user);
    }


    public void changeEmail(String email){

        if(userExistsByEmail(email)){
            throw new EntityExistsException(String.format("邮箱%s已绑定",email));
        }

        String username = tenantAware.getCurrentTenant();
        User user = loadUser(username);
        user.setEmail(email);
        update(user);
    }

    public void changePhone(String phone){

        if(userExistsByPhone(phone)){
            throw new EntityExistsException(String.format("手机%s已绑定",phone));
        }

        String username = tenantAware.getCurrentTenant();
        User user = loadUser(username);
        user.setPhone(phone);
        update(user);
    }

    public void activeUser(String username){
        User user = loadUser(username);
        if(user.getStatus()==Status.REGISTERED){
            user.setStatus(Status.ACTIVED);
            update(user);
        }
    }


    @Override
    public boolean userExists(String username) {
        return ((TenantRepository)getRepository()).existsByUsername(username);
    }


    public boolean userExistsByPhone(String phone){
        return ((TenantRepository)getRepository()).existsByPhone(phone);

    }

    public boolean userExistsByEmail(String email){
        return ((TenantRepository)getRepository()).existsByEmail(email);

    }


    /**
     * FIXME:
     * 支持email或者username登录
      * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User tenant = null;
        if(RegexHelper.isEmail(username)){
            tenant = ((TenantRepository)getRepository()).findByEmail(username);
        }
        else {
           tenant = ((TenantRepository)getRepository()).findByUsername(username);
        }


        if(tenant==null)
            throw new UsernameNotFoundException(String.format("用户%s不存在",username));

        return tenant;
    }

    public boolean addToRole(Long tenantId,Long roleId){
        User tenant = ((TenantRepository)getRepository()).findOne(tenantId);

        if(tenant == null){
            throw new EntityNotFoundException(String.format("用户不存在"));
        }

        IRole role = roleRepository.findOne(roleId);
        if(role == null)
            throw new EntityNotFoundException(String.format("角色不存在"));

        tenant.addRole(role);
        update(tenant);


        return true;
    }

    private User loadUser(String username){ return (User) loadUserByUsername(username);
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ITenantAware getTenantAware() {
        return tenantAware;
    }

    public void setTenantAware(ITenantAware tenantAware) {
        this.tenantAware = tenantAware;
    }

}
