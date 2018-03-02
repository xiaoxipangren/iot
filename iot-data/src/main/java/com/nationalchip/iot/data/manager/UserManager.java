package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.Role;
import com.nationalchip.iot.data.model.User;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.data.repository.RoleRepository;
import com.nationalchip.iot.data.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
@Transactional(readOnly = true)
public class UserManager extends BaseManager<User> implements UserDetailsManager{

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public void createUser(UserDetails user) {
        create((User) user);
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

    }

    @Override
    public boolean userExists(String username) {
        return ((TenantRepository)getRepository()).existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User tenant = ((TenantRepository)getRepository()).findByUsername(username);

        if(tenant == null)
            throw new UsernameNotFoundException(String.format("用户%s不存在",username));

        return tenant;
    }

    public boolean addRole(Long tenantId,Long roleId){
        User tenant = ((TenantRepository)getRepository()).findOne(tenantId);
        if(tenant == null){
            throw new EntityNotFoundException(String.format("用户不存在"));
        }

        Role role = roleRepository.findOne(roleId);
        if(role == null)
            throw new EntityNotFoundException(String.format("角色不存在"));

        return tenant.addRole(role);
    }


}
