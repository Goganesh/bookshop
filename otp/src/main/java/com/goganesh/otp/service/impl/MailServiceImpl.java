package com.goganesh.otp.service.impl;

import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@ConditionalOnProperty(name = "com.goganesh.bookshop.mail-service.provider", havingValue = "enable")
public class MailServiceImpl implements MailService {

    private static final String FROM = "";
    private static final String TITLE = "Bookstore email verification!";
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(@Value("${com.goganesh.bookshop.mail-service.email") String email,
                           @Value("${com.goganesh.bookshop.mail-service.password") String password) {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.ru");
        javaMailSender.setPort(465);
        javaMailSender.setUsername(email);
        javaMailSender.setPassword(password);
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smpts");
        props.put("mail.smpt.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "true");

        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(SendMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(FROM);
        mailMessage.setTo(message.getContact());
        mailMessage.setSubject(TITLE);
        mailMessage.setText(message.getPayload());
        javaMailSender.send(mailMessage);
    }
}
