package com.goganesh.otp.service.impl;

import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String FROM = "";
    private static final String TITLE = "Bookstore email verification!";
    private final JavaMailSender javaMailSender;

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
