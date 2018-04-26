package com.nationalchip.iot.message.mail;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 9:55 AM
 * @Modified:
 */
public interface IMailProvider {
    void send(IMail mail);
    Iterable<IMail> receive(String folder);
}
