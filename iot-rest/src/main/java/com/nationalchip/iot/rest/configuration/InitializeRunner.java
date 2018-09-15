package com.nationalchip.iot.rest.configuration;

import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.Admin;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.security.authority.SecurityConstant;
import com.nationalchip.iot.security.configuration.SecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/7/18 5:21 PM
 * @Modified:
 */
@Component
public class InitializeRunner implements ApplicationRunner {


    @Autowired
    private IUserManager userManager;

    @Autowired
    private SecurityProperty securityProperty;

    @Override
    public void run(ApplicationArguments args) throws Exception {



    }


    private void initAdmin(){
        Admin admin = new Admin();
        String name = securityProperty.getAdmin().getUsername().trim();
        admin.setName(name);

        String password = securityProperty.getAdmin().getPassword().trim();
        admin.setPassword(password);

        String ip = securityProperty.getAdmin().getIp();

        if(ip!=null && !ip.isEmpty()){
            admin.setIp(ip);
            admin.setRestrictIp(true);
        }

        String mac = securityProperty.getAdmin().getMac();

        if(mac!=null && !mac.isEmpty()){
            admin.setMac(mac);
            admin.setRestrictMac(true);
        }

        userManager.create(admin);

    }


    private boolean isInitialized(){

        return userManager.existsByName(securityProperty.getAdmin().getUsername());

    }


}
