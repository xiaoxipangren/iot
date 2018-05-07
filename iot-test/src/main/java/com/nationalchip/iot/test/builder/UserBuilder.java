package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 10:57 AM
 * @Modified:
 */
public class UserBuilder extends NamedBuilder<IUserCreupdate> implements IUserCreupdate {

    protected String phone;
    protected String email;


    @Override
    public IUserCreupdate phone(String phone) {

        this.phone  =phone;

        return this;
    }

    @Override
    public IUserCreupdate email(String email) {
        this.email=email;
        return this;
    }

    @Override
    public IUser create() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPhone(this.phone);

        return user;
    }

    @Override
    public IUser update(IUser iUser) {
        User user = (User) iUser;

        if(name!=null)
            user.setName(name);

        if(email!=null)
            user.setEmail(email);

        if(phone!=null)
            user.setPhone(phone);

        return user;

    }
}
