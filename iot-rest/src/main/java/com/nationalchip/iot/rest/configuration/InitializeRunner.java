package com.nationalchip.iot.rest.configuration;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.data.manager.IAuthorityManager;
import com.nationalchip.iot.data.manager.IRoleManager;
import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.*;
import com.nationalchip.iot.security.configuration.SecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import static com.nationalchip.iot.security.authority.Authority.*;

import java.util.*;

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

    @Autowired
    private IRoleManager roleManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        initAuthorities();
        initRoles();
        initSystemUser();
    }


    private void initSystemUser(){

        Admin system = new Admin();
        String name = securityProperty.getSystem().getUsername().trim();
        system.setName(name);

        String password = securityProperty.getSystem().getPassword().trim();
        system.setPassword(password);

        String ip = securityProperty.getSystem().getIp();

        if(ip!=null && !ip.isEmpty()){
            system.setIp(ip);
            system.setRestrictIp(true);
        }

        String mac = securityProperty.getSystem().getMac();

        if(mac!=null && !mac.isEmpty()){
            system.setMac(mac);
            system.setRestrictMac(true);
        }

        IRole role = roleManager.findByName(SYSTEM);
        system.addRole(role);

        userManager.create(system);

    }

    public InitializeRunner() {
    }

    private void initRoles(){
        initSystemRole();
        initAdminRole();
        initDeveloperRole();
        initConsumerRole();
    }

    private void initSystemRole(){

        Collection<IAuthority> existedAuthorities = Lists.newArrayList(authorityManager.findAll());
        Role role = Role.SystemRole();
        if(!roleManager.existsByName(role.getName())){
            Set<IAuthority> authoritySet = new HashSet<>();
            authoritySet.addAll(existedAuthorities);
            role.setAuthorities(authoritySet);
            roleManager.create(role);
        }

        else{
            role = (Role)roleManager.findByName(SYSTEM);

            Set<IAuthority> authorities = role.getAuthorities();
            authorities.addAll(existedAuthorities);
            roleManager.update(role);
        }

    }

    private void initAdminRole(){
        Role role = Role.AdminRole();
        if(!roleManager.existsByName(role.getName())){
            Collection<String> entities = Arrays.asList(EN_ASSET,EN_DOCUMENT,EN_NEWS,EN_PRODUCT);
            Collection<String> operations = Arrays.asList(OP_CREATE,OP_DELETE,OP_READ,OP_UPDATE);

            Set<IAuthority> authorities  = new HashSet<>();

            for(String entity : entities){
                for(String operation : operations){
                    addAuthority(authorities,operation,entity);
                }
            }

            addAuthority(authorities,OP_READ,EN_USER);
            addAuthority(authorities,OP_UPDATE,EN_USER);

            role.setAuthorities(authorities);
            roleManager.create(role);
        }
    }

    private void initDeveloperRole(){
        Role role = Role.DeveloperRole();
        if(!roleManager.existsByName(role.getName())){
            Collection<String> entities = Arrays.asList(EN_ASSET,EN_DOCUMENT,EN_NEWS,EN_PRODUCT);
            Collection<String> operations = Arrays.asList(OP_READ);

            Set<IAuthority> authorities  = new HashSet<>();

            for(String entity : entities){
                for(String operation : operations){
                    addAuthority(authorities,operation,entity);
                }
            }

            addAuthority(authorities,OP_READ,EN_USER);
            addAuthority(authorities,OP_UPDATE,EN_USER);

            role.setAuthorities(authorities);
            roleManager.create(role);
        }
    }

    private void initConsumerRole(){
        Role role = Role.ConsumerRole();
        if(!roleManager.existsByName(role.getName())){

            Set<IAuthority> authorities  = new HashSet<>();

            addAuthority(authorities,OP_UPDATE,EN_USER);
            addAuthority(authorities,OP_READ,EN_USER);

            role.setAuthorities(authorities);
            roleManager.create(role);
        }
    }


    /**
     * 系统启动时，保证所有的权限常量规定的权限都存储于数据库
     * 该方法应有内置的SYSTEM用户执行
     *
     * @return
     */

    private boolean initAuthorities(){

        return securityContext.runAsSystem(() ->{

            Collection<String> authorities = getAllAuthorities();

            for(String authority : authorities){
                String[] array = authority.split(SEPARATOR);
                Operation operation = Operation.valueOf(array[0]);
                String target = array[1];

                if(!authorityManager.exists(operation,target)){
                    com.nationalchip.iot.data.model.auth.Authority auth = new com.nationalchip.iot.data.model.auth.Authority(operation,target);
                    authorityManager.create(auth);
                }
            }
            return true;
        });
    }


    private void addAuthority(Collection<IAuthority> authorities,String operation,String entity){
        Operation op = Operation.valueOf(operation);
        IAuthority auth = authorityManager.findByOperationAndTarget(op,entity);
        if(auth!=null)
            authorities.add(auth);
    }


    private boolean isInitialized(){

        return userManager.existsByName(securityProperty.getSystem().getUsername());

    }


}
