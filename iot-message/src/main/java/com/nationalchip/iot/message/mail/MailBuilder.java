package com.nationalchip.iot.message.mail;

import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.message.exception.MailException;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/5/18 6:56 PM
 * @Modified:
 */
public class MailBuilder implements IMailBuilder {
    private String subject="Simple Mail";
    private String content="This is a simple mail from nationalchip.";
    private String from;
    private String to;
    private boolean html=true;
    private Date date=new Date(System.currentTimeMillis());

    @Override
    public IMailBuilder date(Date date) {
        this.date=date;
        return this;
    }

    @Override
    public IMailBuilder content(String content) {
        this.content=content;
        return this;
    }

    @Override
    public IMailBuilder from(String from) {
        if(!RegexHelper.isEmail(from)){
            throw new MailException("不是有效的邮箱");
        }
        this.from=from;
        return null;
    }

    @Override
    public IMailBuilder to(String to) {
        if(!RegexHelper.isEmail(to)){
            throw new MailException("不是有效的邮箱");
        }
        this.to=to;
        return this;
    }

    @Override
    public IMailBuilder subject(String subject) {
        this.subject=subject;
        return this;
    }

    @Override
    public IMailBuilder html(boolean html) {
        this.html=html;
        return this;
    }

    @Override
    public IMail build() {
        SimpleMail mail = new SimpleMail(from,to);

        mail.setHtml(html);
        mail.setContent(content);
        mail.setSubject(subject);
        mail.setDate(date);

        return mail;
    }
}
