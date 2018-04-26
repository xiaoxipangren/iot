package com.nationalchip.iot.message.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 10:51 AM
 * @Modified:
 */
@Component
public class MailProvider implements IMailProvider {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(IMail mail) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setTo(mail.getTo());
            messageHelper.setFrom(mail.getFrom()==null?from:mail.getFrom());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getContent(),mail.isHtml());
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new MailException(e.getMessage());
        }
    }

    @Override
    public Iterable<IMail> receive(String folder) {
        return null;
    }
}
