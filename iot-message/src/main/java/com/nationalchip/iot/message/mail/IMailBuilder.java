package com.nationalchip.iot.message.mail;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/5/18 6:55 PM
 * @Modified:
 */
public interface IMailBuilder {
    IMailBuilder content(String content);
    IMailBuilder from(String from);
    IMailBuilder to(String to);
    IMailBuilder subject(String subject);
    IMailBuilder html(boolean html);
    IMailBuilder date(Date date);

    IMail build();
}
