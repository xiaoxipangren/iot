package com.nationalchip.iot.security.authority;

import static com.nationalchip.iot.security.authority.Authority.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 3:27 PM
 * @Modified:
 */
public class AuthorityExpression {

    public static final String BRACKET_OPEN = "(";
    public static final String BRACKET_CLOSE = ")";
    public static final String HAS_AUTH_PREFIX = "hasAuthority" + BRACKET_OPEN + "'";
    public static final String HAS_AUTH_SUFFIX = "'" + BRACKET_CLOSE;
    public static final String HAS_AUTH_AND = " and ";
    public static final String HAS_AUTH_OR = " or ";

    public static final String HAS_AUTH_REGISTER=HAS_AUTH_PREFIX+Authority.REGISTER+HAS_AUTH_SUFFIX;
    public static final String HAS_AUTH_LOGIN=HAS_AUTH_PREFIX+Authority.LOGIN+HAS_AUTH_SUFFIX;
    public static final String HAS_AUTH_RESET_PASSWORD=HAS_AUTH_PREFIX+Authority.RESET_PASSWORD+HAS_AUTH_SUFFIX;
    public static final String HAS_ROLE_SYSTEM=HAS_AUTH_PREFIX+Authority.ROLE_SYSTEM+HAS_AUTH_SUFFIX;


    public static final String HAS_AUTH_CREATE_USER=HAS_AUTH_PREFIX+CREATE_USER+HAS_AUTH_SUFFIX;
    public static final String HAS_AUTH_UPDATE_USER=HAS_AUTH_PREFIX+UPDATE_USER+HAS_AUTH_SUFFIX;
    public static final String HAS_AUTH_DELETE_USER=HAS_AUTH_PREFIX+DELETE_USER+HAS_AUTH_SUFFIX;
    public static final String HAS_AUTH_READ_USER=HAS_AUTH_PREFIX+READ_USER+HAS_AUTH_SUFFIX;






}
