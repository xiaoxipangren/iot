package com.nationalchip.iot.message.mail;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 10:29 AM
 * @Modified:
 */
public interface IMail {
    String getFrom();
    String getTo();
    String getContent();
    String getSubject();
    Date getDate();
    boolean isHtml();
}
