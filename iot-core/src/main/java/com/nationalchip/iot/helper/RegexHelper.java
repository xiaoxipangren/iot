package com.nationalchip.iot.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public static boolean isPhone(String phone){
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher matcher = p.matcher(phone);
        return matcher.matches();
    }

    public static Iterable<String> extract(String content,String pattern){
        Pattern p = Pattern.compile(pattern,Pattern.MULTILINE);
        Matcher matcher = p.matcher(content);

        if(matcher == null)
            return Collections.emptyList();

        List<String> result = new ArrayList<>();

        while(matcher.find()){
            result.add(matcher.group());
        }

        return result;
    }
}
