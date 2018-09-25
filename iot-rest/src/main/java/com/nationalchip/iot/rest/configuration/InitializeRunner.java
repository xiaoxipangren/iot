package com.nationalchip.iot.rest.configuration;

import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.data.manager.IAuthorityManager;
import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.Admin;
import com.nationalchip.iot.data.model.auth.Operation;
import com.nationalchip.iot.security.authority.Authority;
import com.nationalchip.iot.security.configuration.SecurityProperty;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
    private IAuthorityManager authorityManager;

    @Autowired
    private SecurityProperty securityProperty;

    @Autowired
    private ISecurityContext securityContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        securityContext.runAsSystem(this::initAuthorities);
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

    private void initRoles(){

    }


    private boolean initAuthorities(){
        Collection<String> authorities = Authority.getAllAuthorities();

        for(String authority : authorities){
            String[] array = authority.split(Authority.SEPARATOR);
            Operation operation = Operation.valueOf(array[0]);
            String target = array[1];

            if(!authorityManager.exists(operation,target)){
                com.nationalchip.iot.data.model.auth.Authority auth = new com.nationalchip.iot.data.model.auth.Authority(operation,target);
                authorityManager.create(auth);
            }


        }

        return true;


    }


    private boolean isInitialized(){

        return userManager.existsByName(securityProperty.getAdmin().getUsername());

    }


}
