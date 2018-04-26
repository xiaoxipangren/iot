package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.message.mail.IMail;
import com.nationalchip.iot.message.mail.IMailProvider;
import com.nationalchip.iot.message.mail.MailException;
import com.nationalchip.iot.message.mail.SimpleMail;
import com.nationalchip.iot.rest.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/22/18 2:28 PM
 * @Modified:
 */
@Service
public class MailService {

    @Autowired
    private IMailProvider mailProvider;

    public void sendSimple(String from,String to, String title, String content,boolean html){

        try{
            SimpleMail mail = new SimpleMail(from,to);
            mail.setSubject(title);
            mail.setContent(content);
            mail.setHtml(html);

            mailProvider.send(mail);
        }
        catch (MailException me){
            throw new AuthException(me.getMessage());
        }

    }

    public void sendSimple(String to, String title, String content,boolean html){
        sendSimple(null,to,title,content,html);
    }





}
