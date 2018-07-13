package com.nationalchip.iot.security.authority;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 3:27 PM
 * @Modified:
 */
public class Authority {

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String RESET_PASSWORD = "resetpwd";
    public static final String ROLE_SYSTEM="role_system";

    public static final String SEPARATOR = "_";

    public static final String OP_READ="read";
    public static final String OP_UPDATE="update";
    public static final String OP_CREATE="create";
    public static final String OP_DELETE="delete";

    public static final String EN_USER="user";




    public static final String READ_USER=OP_READ+ SEPARATOR +EN_USER;
    public static final String UPDATE_USER=OP_READ+ SEPARATOR +EN_USER;
    public static final String DELETE_USER=OP_READ+ SEPARATOR +EN_USER;
    public static final String CREATE_USER=OP_READ+ SEPARATOR +EN_USER;



}
