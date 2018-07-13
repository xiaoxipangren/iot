package com.nationalchip.iot.message.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/22/18 2:28 PM
 * @Modified:
 */
@Service
public class MailService implements IMailService{

    @Autowired
    private IMailProvider mailProvider;

    @Override
    public void send(String from, String to, String title, String content, boolean html) {
        SimpleMail mail = new SimpleMail(from,to);
        mail.setSubject(title);
        mail.setContent(content);
        mail.setHtml(html);

        mailProvider.send(mail);
    }

    @Override
    public void send(IMail mail) {
        mailProvider.send(mail);
    }

    @Override
    public void send(IMailBuilder builder) {
        IMail mail = builder.build();
        mailProvider.send(mail);
    }


}
