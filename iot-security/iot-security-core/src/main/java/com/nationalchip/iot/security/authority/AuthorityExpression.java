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
    public static final String HAS_SUFFIX = "'" + BRACKET_CLOSE;

    public static final String HAS_ROLE_PREFIX = "hasRole" + BRACKET_OPEN + "'";

    public static final String AND = " and ";
    public static final String OR = " or ";

    public static final String HAS_AUTH_REGISTER=HAS_AUTH_PREFIX + AUTH_REGISTER + HAS_SUFFIX;
    public static final String HAS_AUTH_LOGIN=HAS_AUTH_PREFIX + AUTH_LOGIN + HAS_SUFFIX;
    public static final String HAS_AUTH_RESET_PASSWORD=HAS_AUTH_PREFIX+ AUTH_RESET_PASSWORD + HAS_SUFFIX;

    public static final String HAS_ROLE_SYSTEM=HAS_ROLE_PREFIX + SYSTEM + HAS_SUFFIX;
    public static final String HAS_ROLE_ADMIN=HAS_ROLE_PREFIX + ADMIN + HAS_SUFFIX;


    public static final String HAS_AUTH_CREATE_USER=HAS_AUTH_PREFIX+ AUTH_CREATE_USER + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_USER=HAS_AUTH_PREFIX+ AUTH_UPDATE_USER + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_USER=HAS_AUTH_PREFIX+ AUTH_DELETE_USER + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_USER=HAS_AUTH_PREFIX+ AUTH_READ_USER + HAS_SUFFIX;






}
