package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.builder.UserBuilder;
import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.data.repository.TenantRepository;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Component
@Transactional(readOnly = true)
public class UserManager extends NamedManager<IUser,User> implements IUserManager{

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
        if(existsByEmail(user.getEmail())){
            throw new EntityExistsException(String.format("邮箱%s已绑定",user.getEmail()));
        }
        if(existsByPhone(user.getPhone())){
            throw new EntityExistsException(String.format("手机%s已绑定",user.getPhone()));
        }

        user.setStatus(Status.REGISTERED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        create(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        update((IUser) user);
    }

    @Override
    public void deleteUser(String username) {
        repository().deleteByName(username);
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

    @Override
    public void activateUser(String username) {
        User user = loadUser(username);
        if(user.getStatus()==Status.REGISTERED){
            user.setStatus(Status.ACTIVED);
            update(user);
        }
    }

    public void resetPassword(String username, String password){
        User user = loadUser(username);
        user.setPassword(passwordEncoder.encode(password));
        update(user);
    }


    public void changeEmail(String email){

        if(existsByEmail(email)){
            throw new EntityExistsException(String.format("邮箱%s已绑定",email));
        }

        User user = loadCurrentUser();
        user.setEmail(email);
        update(user);
    }

    public void changePhone(String phone){

        if(existsByPhone(phone)){
            throw new EntityExistsException(String.format("手机%s已绑定",phone));
        }

        User user = loadCurrentUser();
        user.setPhone(phone);
        update(user);
    }


    @Override
    public boolean userExists(String username) {
        return repository().existsByName(username);
    }


    public boolean existsByPhone(String phone){
        return repository().existsByPhone(phone);

    }

    @Override
    public IUser findByPhone(String phone) {
        IUser user =  repository().findByPhone(phone);
        if(user == null)
            throw new EntityNotFoundException(String.format("手机%未绑定",phone));
        return user;
    }

    @Override
    public boolean isInRole(IRole role) {
        User user = loadCurrentUser();
        return user.isInRole(role);

    }

    @Override
    public void addToRole(IRole role) {
        User user = loadCurrentUser();
        user.addRole(role);

        update(user);
    }

    @Override
    public void removeFromRole(IRole role) {
        User user = loadCurrentUser();
        user.removeRole(role);
        update(user);
    }

    public boolean existsByEmail(String email){
        return repository().existsByEmail(email);

    }

    @Override
    public IUser findByEmail(String email) {
        IUser user = repository().findByEmail(email);
        if(user == null)
            throw new EntityNotFoundException(String.format("邮箱%未绑定",email));
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IUser user = null;
        if(RegexHelper.isEmail(username)){
            user = repository().findByEmail(username);
            if(user==null)
                throw new EntityNotFoundException(String.format("邮箱%s未注册",username));
        }
        else if(RegexHelper.isPhone(username)){
            user = repository().findByPhone(username);
            if(user==null)
                throw new EntityNotFoundException(String.format("手机号%s未注册",username));
        }
        else{
            user = repository().findByName(username);
            if(user==null)
                throw new UsernameNotFoundException(String.format("用户%s不存在",username));
        }
        return user;
    }

    @Override
    protected void preCreate(IUser iUser) {
        super.preCreate(iUser);
        User user = (User)iUser;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVED);
    }

    @Override
    protected void postUpdate(IUser iUser) {
        User user = (User)iUser;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void setTenantAware(ITenantAware tenantAware) {
        this.tenantAware = tenantAware;
    }


    private TenantRepository repository(){
        IRepository<User> repository = getRepository();

        if(getRepository() instanceof TenantRepository){
            return (TenantRepository)repository;
        }
        else
            throw new NotImplementedException();

    }

    private User loadCurrentUser(){
        String username = tenantAware.getCurrentTenant();
        return loadUser(username);
    }
    private User loadUser(String username){
        return (User) loadUserByUsername(username);
    }

}
