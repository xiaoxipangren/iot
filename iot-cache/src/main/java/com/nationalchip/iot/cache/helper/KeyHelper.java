package com.nationalchip.iot.cache.helper;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/23/18 5:06 PM
 * @Modified:
 */
public class KeyHelper {
    public static String tokenExpirationKey(String username){
        return String.format("%s-token-expired",username);
    }

    public static String resetPasswordKey(String email){
        return String.format("%s-reset-password",email);
    }

    public static String validateEmailKey(String email){
        return String.format("%s-validate-email",email);
    }
}

