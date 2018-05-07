package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.data.model.auth.IUser;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 1:09 PM
 * @Modified:
 */
public interface IUserManager extends UserDetailsManager,INamedManager<IUser>{

    void activateUser(String username);
    void resetPassword(String username,String password);

    void changeEmail(String email);
    boolean existsByEmail(String email);
    IUser findByEmail(String email);


    void changePhone(String phone);
    boolean existsByPhone(String phone);
    IUser findByPhone(String phone);

    boolean isInRole(IRole role);
    void addToRole(IRole role);
    void removeFromRole(IRole role);
}
