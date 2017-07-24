package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件
 */
@Service
public class MailServiceImpl implements IMailService {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.from}")
    private String from;

    //身份认证
    private static Properties properties = new Properties();
    static{
        properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.socketFactory.port", "25");
        //properties.put("mail.smtp.socketFactory.fallback", "false");
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.timeout", "25000");
    }

    public void sendMail(String target, String title, String content) {
        try {
            //创建邮件发送对象
            JavaMailSenderImpl sender = new JavaMailSenderImpl();

            //设置发送邮件服务器
            sender.setHost(host);

            //建立邮件对象
            MimeMessage mimeMessage = sender.createMimeMessage();
            //创建邮件助手
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");

            //设置收件人，发件人,标题，内容
            messageHelper.setTo(target);
            messageHelper.setFrom(from);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);

            //设置发送邮件的账号密码
            sender.setUsername(username);
            sender.setPassword(password);

            sender.setJavaMailProperties(properties);
            //发送邮件
            sender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败");
        }
    }
}
