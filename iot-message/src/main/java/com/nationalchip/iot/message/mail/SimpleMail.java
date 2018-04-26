package com.nationalchip.iot.message.mail;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 10:38 AM
 * @Modified:
 */
public class SimpleMail implements IMail {

    private String from;
    private String to;
    private String subject;
    private String content;
    private boolean html;
    private Date date;


    private SimpleMail(){
        this.subject="Simple Mail";
        this.content="This is a simple mail from nationalchip.";
        this.html=false;
        this.date=new Date(System.currentTimeMillis());
    }

    public SimpleMail(String to){
        this();
        this.to=to;
    }

    public SimpleMail(String from,String to){
        this(to);
        this.from=from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setHtml(boolean html) {
        this.html = html;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isHtml() {
        return html;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
