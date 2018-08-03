package com.nationalchip.iot.security.configuration;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/20/18 5:11 PM
 * @Modified:
 */
public class RestMappingConstant {

    public static final String REST_BASE_MAPPING = "/rest/v1";

    public static final String REST_AUTH_MAPPING = "/auth";

    public static final String REST_USER_MAPPING = "/users";

    public static final String REST_ASSET_MAPPING = "/assets";

    public static final String REST_NEWS_MAPPING = "/news";

    public static final String REST_MAIL_MAPPING = "/mails";

    public static final String REST_CAPTCHA_MAPPING = "/captcha";

    public static final String REST_ID_MAPPING="/{id:.+}";



    public static final String REST_LIST_ACTION = "/list";

    public static final String REST_CATEGORY_ACTION = "/category";

    public static final String REST_DOWNLOAD_ACTION = REST_ID_MAPPING+"/download";

    public static final String REST_UPLOAD_ACTION = REST_ID_MAPPING+"/upload";

    public static final String REST_LOGIN_ACTION = "/login";

    public static final String REST_REGISTER_ACTION = "/register";

    public static final String REST_VALIDATE_ACTION = "/validate";

    public static final String REST_EXISTS_ACTION = "/exists";

    public static final String REST_SENDMAIL_ACTION = "/sendmail";

    public static final String REST_RESETPWD_ACTION = "/resetpwd";


    public static final String REST_JWT_HEADER = "Authorization";
    public static final String REST_JWT_PREFIX = "Bearer ";

}
