package com.nationalchip.iot.security.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 3:27 PM
 * @Modified:
 */
public class Authority {

    private static Logger logger = LoggerFactory.getLogger(Authority.class);

    public static final int TYPE_DEVELOPER=0;
    public static final int TYPE_CONSUMER=1;
    public static final int TYPE_ADMIN=2;


    public static final String SEPARATOR = "_";
    public static final String ROLE_PREFIX="ROLE"+SEPARATOR;


    public static final String SYSTEM ="SYSTEM";
    public static final String ROLE_SYSTEM=ROLE_PREFIX+SYSTEM;
    public static final String ADMIN="ADMIN";
    public static final String ROLE_ADMIN=ROLE_PREFIX+ADMIN;
    public static final String DEVELOPER="DEVELOPER";
    public static final String ROLE_DEVELOPER=ROLE_PREFIX+DEVELOPER;
    public static final String CONSUMER="CONSUMER";
    public static final String ROLE_CONSUMER=ROLE_PREFIX+CONSUMER;



    public static final String AUTH_REGISTER = "REGISTER";
    public static final String AUTH_LOGIN = "LOGIN";
    public static final String AUTH_RESET_PASSWORD = "RESET_PASSWORD";

    public static final String OP_READ="READ";
    public static final String OP_UPDATE="UPDATE";
    public static final String OP_CREATE="CREATE";
    public static final String OP_DELETE="DELETE";

    public static Collection<String> getAllAuthorities(){
        Collection<String> authorities= new ArrayList<>();
        Collection<String> operations = Arrays.asList(OP_READ,OP_CREATE,OP_DELETE,OP_UPDATE);
        for(Field field : Authority.class.getDeclaredFields()){
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {

                String name = field.getName();

                String[] array = name.split("_");
                if(array.length<3)
                    continue;

                if(!"AUTH".equals(array[0]) || !operations.contains(array[1]))
                    continue;


                field.setAccessible(true);
                try {
                    final String authority = (String) field.get(null);
                    authorities.add(authority);
                } catch (final IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        return authorities;

    }

    public static final String EN_ADMIN = "USER";
    public static final String AUTH_READ_ADMIN = OP_READ + SEPARATOR + EN_ADMIN;
    public static final String AUTH_CREATE_ADMIN = OP_CREATE + SEPARATOR + EN_ADMIN;
    public static final String AUTH_DELETE_ADMIN = OP_DELETE + SEPARATOR + EN_ADMIN;
    public static final String AUTH_UPDATE_ADMIN = OP_UPDATE + SEPARATOR + EN_ADMIN;


    public static final String EN_DEVELOPER = "DEVELOPER";
    public static final String AUTH_READ_DEVELOPER = OP_READ + SEPARATOR + EN_DEVELOPER;
    public static final String AUTH_CREATE_DEVELOPER = OP_CREATE + SEPARATOR + EN_DEVELOPER;
    public static final String AUTH_DELETE_DEVELOPER = OP_DELETE + SEPARATOR + EN_DEVELOPER;
    public static final String AUTH_UPDATE_DEVELOPER = OP_UPDATE + SEPARATOR + EN_DEVELOPER;

    public static final String EN_CONSUMER = "CONSUMER";
    public static final String AUTH_READ_CONSUMER = OP_READ + SEPARATOR + EN_CONSUMER;
    public static final String AUTH_CREATE_CONSUMER = OP_CREATE + SEPARATOR + EN_CONSUMER;
    public static final String AUTH_DELETE_CONSUMER = OP_DELETE + SEPARATOR + EN_CONSUMER;
    public static final String AUTH_UPDATE_CONSUMER = OP_UPDATE + SEPARATOR + EN_CONSUMER;

    public static final String EN_USER = "USER";
    public static final String AUTH_READ_USER = OP_READ + SEPARATOR + EN_USER;
    public static final String AUTH_CREATE_USER = OP_CREATE + SEPARATOR + EN_USER;
    public static final String AUTH_DELETE_USER = OP_DELETE + SEPARATOR + EN_USER;
    public static final String AUTH_UPDATE_USER = OP_UPDATE + SEPARATOR + EN_USER;

    public static final String EN_ROLE = "ROLE";
    public static final String AUTH_READ_ROLE = OP_READ + SEPARATOR + EN_ROLE;
    public static final String AUTH_CREATE_ROLE = OP_CREATE + SEPARATOR + EN_ROLE;
    public static final String AUTH_DELETE_ROLE = OP_DELETE + SEPARATOR + EN_ROLE;
    public static final String AUTH_UPDATE_ROLE = OP_UPDATE + SEPARATOR + EN_ROLE;

    public static final String EN_ASSET = "ASSET";
    public static final String AUTH_READ_ASSET = OP_READ + SEPARATOR + EN_ASSET;
    public static final String AUTH_CREATE_ASSET = OP_CREATE + SEPARATOR + EN_ASSET;
    public static final String AUTH_DELETE_ASSET = OP_DELETE + SEPARATOR + EN_ASSET;
    public static final String AUTH_UPDATE_ASSET = OP_UPDATE + SEPARATOR + EN_ASSET;

    public static final String EN_DOCUMENT = "DOCUMENT";
    public static final String AUTH_READ_DOCUMENT = OP_READ + SEPARATOR + EN_DOCUMENT;
    public static final String AUTH_CREATE_DOCUMENT = OP_CREATE + SEPARATOR + EN_DOCUMENT;
    public static final String AUTH_DELETE_DOCUMENT = OP_DELETE + SEPARATOR + EN_DOCUMENT;
    public static final String AUTH_UPDATE_DOCUMENT = OP_UPDATE + SEPARATOR + EN_DOCUMENT;

    public static final String EN_NEWS = "NEWS";
    public static final String AUTH_READ_NEWS = OP_READ + SEPARATOR + EN_NEWS;
    public static final String AUTH_CREATE_NEWS = OP_CREATE + SEPARATOR + EN_NEWS;
    public static final String AUTH_DELETE_NEWS = OP_DELETE + SEPARATOR + EN_NEWS;
    public static final String AUTH_UPDATE_NEWS = OP_UPDATE + SEPARATOR + EN_NEWS;

    public static final String EN_PRODUCT = "PRODUCT";
    public static final String AUTH_READ_PRODUCT = OP_READ + SEPARATOR + EN_PRODUCT;
    public static final String AUTH_CREATE_PRODUCT = OP_CREATE + SEPARATOR + EN_PRODUCT;
    public static final String AUTH_DELETE_PRODUCT = OP_DELETE + SEPARATOR + EN_PRODUCT;
    public static final String AUTH_UPDATE_PRODUCT = OP_UPDATE + SEPARATOR + EN_PRODUCT;


}
