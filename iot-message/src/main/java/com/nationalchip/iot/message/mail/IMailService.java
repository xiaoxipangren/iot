package com.nationalchip.iot.message.mail;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 4:48 PM
 * @Modified:
 */
public interface IMailService {
    void send (String from,String to, String title, String content,boolean html);
    void send(IMail mail);
    void send(IMailBuilder builder);
}
