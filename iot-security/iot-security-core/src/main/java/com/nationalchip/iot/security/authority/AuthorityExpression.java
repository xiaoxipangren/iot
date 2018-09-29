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


    public static final String HAS_AUTH_CREATE_ADMIN=HAS_AUTH_PREFIX+ AUTH_CREATE_ADMIN + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_ADMIN=HAS_AUTH_PREFIX+ AUTH_UPDATE_ADMIN + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_ADMIN=HAS_AUTH_PREFIX+ AUTH_DELETE_ADMIN + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_ADMIN=HAS_AUTH_PREFIX+ AUTH_READ_ADMIN + HAS_SUFFIX;


    public static final String HAS_AUTH_CREATE_DEVELOPER=HAS_AUTH_PREFIX+ AUTH_CREATE_DEVELOPER + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_DEVELOPER=HAS_AUTH_PREFIX+ AUTH_UPDATE_DEVELOPER + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_DEVELOPER=HAS_AUTH_PREFIX+ AUTH_DELETE_DEVELOPER + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_DEVELOPER=HAS_AUTH_PREFIX+ AUTH_READ_DEVELOPER + HAS_SUFFIX;

    public static final String HAS_AUTH_CREATE_CONSUMER=HAS_AUTH_PREFIX+ AUTH_CREATE_CONSUMER + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_CONSUMER=HAS_AUTH_PREFIX+ AUTH_UPDATE_CONSUMER + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_CONSUMER=HAS_AUTH_PREFIX+ AUTH_DELETE_CONSUMER + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_CONSUMER=HAS_AUTH_PREFIX+ AUTH_READ_CONSUMER + HAS_SUFFIX;

    public static final String HAS_AUTH_CREATE_ASSET=HAS_AUTH_PREFIX+ AUTH_CREATE_ASSET + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_ASSET=HAS_AUTH_PREFIX+ AUTH_UPDATE_ASSET + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_ASSET=HAS_AUTH_PREFIX+ AUTH_DELETE_ASSET + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_ASSET=HAS_AUTH_PREFIX+ AUTH_READ_ASSET + HAS_SUFFIX;


    public static final String HAS_AUTH_CREATE_ROLE=HAS_AUTH_PREFIX+ AUTH_CREATE_ROLE + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_ROLE=HAS_AUTH_PREFIX+ AUTH_UPDATE_ROLE + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_ROLE=HAS_AUTH_PREFIX+ AUTH_DELETE_ROLE + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_ROLE=HAS_AUTH_PREFIX+ AUTH_READ_ROLE + HAS_SUFFIX;


    public static final String HAS_AUTH_CREATE_DOCUMENT=HAS_AUTH_PREFIX+ AUTH_CREATE_DOCUMENT + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_DOCUMENT=HAS_AUTH_PREFIX+ AUTH_UPDATE_DOCUMENT + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_DOCUMENT=HAS_AUTH_PREFIX+ AUTH_DELETE_DOCUMENT + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_DOCUMENT=HAS_AUTH_PREFIX+ AUTH_READ_DOCUMENT + HAS_SUFFIX;

    public static final String HAS_AUTH_CREATE_NEWS=HAS_AUTH_PREFIX+ AUTH_CREATE_NEWS + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_NEWS=HAS_AUTH_PREFIX+ AUTH_UPDATE_NEWS + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_NEWS=HAS_AUTH_PREFIX+ AUTH_DELETE_NEWS + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_NEWS=HAS_AUTH_PREFIX+ AUTH_READ_NEWS + HAS_SUFFIX;

    public static final String HAS_AUTH_CREATE_PRODUCT=HAS_AUTH_PREFIX+ AUTH_CREATE_PRODUCT + HAS_SUFFIX;
    public static final String HAS_AUTH_UPDATE_PRODUCT=HAS_AUTH_PREFIX+ AUTH_UPDATE_PRODUCT + HAS_SUFFIX;
    public static final String HAS_AUTH_DELETE_PRODUCT=HAS_AUTH_PREFIX+ AUTH_DELETE_PRODUCT + HAS_SUFFIX;
    public static final String HAS_AUTH_READ_PRODUCT=HAS_AUTH_PREFIX+ AUTH_READ_PRODUCT + HAS_SUFFIX;

}
