package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.auth.*;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 1:55 PM
 * @Modified:
 */
public class UserBuilder extends NamedCreupdate<IUserBuilder,IUser> implements IUserBuilder {

    private String phone;
    private String email;
    private int type;
    private String password;
    private String avatar;

    @Override
    public IUserBuilder phone(String phone) {
        this.phone=phone;

        return self();
    }

    @Override
    public IUserBuilder email(String email) {
        this.email=email;
        return self();
    }

    @Override
    public IUserBuilder type(int type) {
        this.type=type;
        return self();
    }

    @Override
    public IUserBuilder password(String password) {

        if(password.startsWith("$2a$10$")){
            throw new BadCredentialsException("密码不合法");
        }

        this.password=password;
        return self();
    }

    @Override
    public IUserBuilder avatar(String avatar) {
        this.avatar=avatar;
        return self();
    }

    @Override
    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    @Override
    public Optional<String> getEmail() {
        return Optional.of(email);
    }

    @Override
    protected User newInstance() {
        User user = null;
        switch (type){
            case 0:
                user = new Developer(getName().get(),password);
                break;
            case 1:
                user = new Consumer(getName().get(),password) ;
                break;
            case 2:
                user = new Admin(getName().get(),password);
                break;
            default:
                user = new Developer(getName().get(),password);
                break;

        }
        return user;
    }


    @Override
    protected void apply(IUser entity) {
        super.apply(entity);
        this.<User>tryCast(entity).ifPresent(
                user ->{
                    if(password!=null && !user.getPassword().equals(password)){
                        user.setPassword(password);
                    }
                    if(phone!=null && !phone.equals(user.getPhone())){
                        user.setPhone(phone);
                    }
                    if(email!=null && !email.equals(user.getEmail()))
                        user.setEmail(email);
                    if(avatar!=null)
                        user.setAvatar(avatar);
                    else{
                        //设置默认的头像
                        user.setAvatar("avatar.jpg");
                    }

                }
        );
    }
}
