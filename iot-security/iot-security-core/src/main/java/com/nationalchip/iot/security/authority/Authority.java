package com.nationalchip.iot.security.authority;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 3:27 PM
 * @Modified:
 */
public class Authority {

    public static final int TYPE_DEVELOPER=0;
    public static final int TYPE_CONSUMER=1;
    public static final int TYPE_ADMIN=2;


    public static final String REGISTER = "REGISTER";
    public static final String LOGIN = "LOGIN";
    public static final String RESET_PASSWORD = "RESETPWD";

    public static final String SYSTEM ="SYSTEM";
    public static final String ADMIN="ADMIN";

    public static final String SEPARATOR = "_";
    public static final String ROLE_PREFIX="ROLE"+SEPARATOR;


    public static final String OP_READ="READ";
    public static final String OP_UPDATE="UPDATE";
    public static final String OP_CREATE="CREATE";
    public static final String OP_DELETE="DELETE";

    public static final String EN_USER="USER";
    public static final String EN_ADMIN = "ADMIN";
    public static final String EN_DEVELOPER = "DEVELOPER";
    public static final String EN_ROLE = "ROLE";






    public static final String READ_USER=OP_READ + SEPARATOR + EN_USER;
    public static final String UPDATE_USER=OP_READ + SEPARATOR + EN_USER;
    public static final String DELETE_USER=OP_READ + SEPARATOR + EN_USER;
    public static final String CREATE_USER=OP_READ + SEPARATOR + EN_USER;



}
