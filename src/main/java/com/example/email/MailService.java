package com.example.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件服务类
 * 该类用于发送简单的邮件，使用Spring的JavaMailSender进行邮件发送。
 */
@Component
public class MailService {

    @Autowired
    JavaMailSender javaMailSender; // 注入JavaMailSender，用于发送邮件

    /**
     * 发送简单邮件
     *
     * @param from 邮件发送者
     * @param to 收件人
     * @param cc 抄送人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String from, String to, String cc, String subject, String content) {
        // 创建一个SimpleMailMessage对象，用于配置邮件信息
        SimpleMailMessage simpMsg = new SimpleMailMessage();
        simpMsg.setFrom(from); // 设置邮件发送者
        simpMsg.setTo(to); // 设置收件人
        simpMsg.setCc(cc); // 设置抄送人
        simpMsg.setSubject(subject); // 设置邮件主题
        simpMsg.setText(content); // 设置邮件内容
        javaMailSender.send(simpMsg); // 发送邮件
    }
}
