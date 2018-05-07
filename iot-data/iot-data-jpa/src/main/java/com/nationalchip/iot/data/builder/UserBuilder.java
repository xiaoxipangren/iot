package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.Admin;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.model.hub.Developer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 1:55 PM
 * @Modified:
 */
public class UserBuilder extends NamedCreupdate<IUserBuilder> implements IUserBuilder {

    private String phone;
    private String email;
    private int type;
    private String password;

    @Override
    public IUserBuilder phone(String phone) {
        this.phone=phone;

        return this;
    }

    @Override
    public IUserBuilder email(String email) {
        this.email=email;
        return this;
    }

    @Override
    public IUserBuilder type(int type) {
        this.type=type;
        return this;
    }

    @Override
    public IUserBuilder password(String password) {
        this.password=password;
        return this;
    }

    @Override
    public IUser create() {
        User user = null;
        switch (type){
            case 0:
                user = new Developer(getName().get(),password);
                break;
            case 1:
                user = new Admin(getName().get(),password);
                break;
            default:
                user = new Developer(getName().get(),password);

        }
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        return user;
    }




    @Override
    public void update(final IUser iUser) {
        User user = (User) iUser;

        if(password!=null){
            user.setPassword(password);
        }
        if(phone!=null){
            user.setPhone(phone);
        }
        if(email!=null)
            user.setEmail(email);


    }
}
