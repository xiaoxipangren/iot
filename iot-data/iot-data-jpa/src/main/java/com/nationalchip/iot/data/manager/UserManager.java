package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.common.io.IFileRepository;
import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.configuration.DataProperty;
import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.repository.IRepository;
import com.nationalchip.iot.data.repository.UserRepository;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.io.InputStream;

@Component
@Transactional(readOnly = true)
public class UserManager extends NamedManager<IUser,User> implements IUserManager{

    private static final String AVATAR_PATH="image/avatar";

    @Autowired
    @Qualifier("hashFileRepository")
    private IFileRepository fileRepository;

    @Autowired
    private DataProperty dataProperty;

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
        user.setAvatar("avatar.jpg");
        create(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        update((IUser) user);
    }

    @Override
    protected void preUpdate(IUser iUser) {
        super.preUpdate(iUser);

        encryptPassword(iUser);

    }

    @Override
    public void deleteUser(String username) {
        repository().deleteByName(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String username = tenantAware.getCurrentTenant();
        User user = loadUser(username);

        if(passwordEncoder.matches(oldPassword,user.getPassword()   )){
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

    @Override
    protected IUser loadEntity(IBuilder<IUser> builder) {
        IUser t = super.loadEntity(builder);

        if(t == null && builder instanceof IUserBuilder){
            IUserBuilder userBuilder = (IUserBuilder)builder;
            if(userBuilder.getEmail().isPresent()){
                t = getRepository().findByEmail(userBuilder.getEmail().get());
            }
            else if(userBuilder.getPhone().isPresent())
                t = getRepository().findByPhone(userBuilder.getEmail().get());
        }

        return t;
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
        IUser user = loadCurrentUser();
        return user.isInRole(role);

    }

    @Override
    public void addToRole(IRole role) {
        IUser user = loadCurrentUser();
        user.addRole(role);

        update(user);
    }

    @Override
    public String changeAvatar(InputStream stream) {
        String avatar = saveAvatar(stream);

        User user = loadCurrentUser();
        user.setAvatar(avatar);

        update(user);

        return avatar;
    }


    @Override
    public void removeFromRole(IRole role) {
        IUser user = loadCurrentUser();
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
        user.setStatus(Status.ACTIVED);

        encryptPassword(iUser);
    }



    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void setTenantAware(ITenantAware tenantAware) {
        this.tenantAware = tenantAware;
    }


    public UserRepository repository(){
        IRepository<User> repository = getRepository();

        if(getRepository() instanceof UserRepository){
            return (UserRepository)repository;
        }
        else
            throw new NotImplementedException();

    }

    @Override
    public UserRepository getRepository() {
        return (UserRepository)super.getRepository();

    }

    private User loadCurrentUser(){
        String username = tenantAware.getCurrentTenant();
        return loadUser(username);
    }
    private User loadUser(String username){
        return (User) loadUserByUsername(username);
    }

    private boolean isEncrypted(String password){
        if(password==null)
            return false;

        return password.startsWith("$2a$10$");
    }


    private void encryptPassword(IUser user){
        if(user.getPassword()!=null && !isEncrypted(user.getPassword())){
            User u =(User)user;
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }
    }

    private String saveAvatar(InputStream avatar){
        String sha1 = fileRepository.store( avatar,null, dataProperty.getNginx().getLocation(),AVATAR_PATH);
        return fileRepository.get(sha1,dataProperty.getNginx().getLocation(),AVATAR_PATH).getAbsolutePath()
                .replace(dataProperty.getNginx().getLocation(),dataProperty.getNginx().getServer());
    }

}
