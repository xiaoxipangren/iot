package com.nationalchip.iot.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/23/18 9:35 PM
 * @Modified:
 */
public class RegexHelper {
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
            Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
