package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:32 PM
 * @Modified:
 */
public class App {
    public static void main(String[] args){

        IUserCreupdate builder = new UserBuilder();
        IUser user = builder.name("name")
                .email("email")
                .phone("phone")
                .create();

        System.out.println(user.getName()+","+user.getEmail());

        builder = new UserBuilder();
        builder.name("newName");
        IUser newUser = builder.update(user);
        System.out.println(user.getName()+","+user.getEmail());




    }
}
