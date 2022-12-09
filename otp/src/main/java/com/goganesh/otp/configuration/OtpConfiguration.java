package com.goganesh.otp.configuration;

import com.goganesh.bookshop.model.configuration.ModelConfiguration;
import com.goganesh.bookshop.model.service.UserContactReadRepository;
import com.goganesh.bookshop.model.service.UserContactWriteRepository;
import com.goganesh.otp.service.CodeGeneratorService;
import com.goganesh.otp.service.MailService;
import com.goganesh.otp.service.OtpService;
import com.goganesh.otp.service.SmsService;
import com.goganesh.otp.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Import({ModelConfiguration.class})
public class OtpConfiguration {

    @Bean
    public CodeGeneratorService codeGeneratorService(@Value("${com.goganesh.bookshop.code-generator.symbols}")int symbols) {
        return new CodeGeneratorServiceImpl(symbols);
    }

    @Bean
    @ConditionalOnProperty(name = "com.goganesh.bookshop.mail-service.provider", havingValue = "disable")
    public MailService mailService() {
        return new MailNoOpServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "com.goganesh.bookshop.mail-service.provider", havingValue = "enable")
    public MailService mailService(@Value("${com.goganesh.bookshop.mail-service.email") String email,
                                   @Value("${com.goganesh.bookshop.mail-service.password") String password) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.ru");
        javaMailSender.setPort(465);
        javaMailSender.setUsername(email);
        javaMailSender.setPassword(password);
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smpts");
        props.put("mail.smpt.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.debug","true");

        return new MailServiceImpl(javaMailSender);
    }

    @Bean
    @ConditionalOnProperty(name = "com.goganesh.bookshop.sms-service.provider", havingValue = "disable")
    public SmsService smsService() {
        return new SmsNoOpServiceImpl();
    }

    @Bean
    public OtpService otpService(SmsService smsService,
                                 MailService mailService,
                                 @Value("${com.goganesh.bookshop.sms-service.otp-expire-second}")int otpSmsExpireSecond,
                                 @Value("${com.goganesh.bookshop.mail-service.otp-expire-second}")int otpMailExpireSecond,
                                 CodeGeneratorService codeGeneratorService,
                                 UserContactReadRepository userContactReadRepository,
                                 UserContactWriteRepository userContactWriteRepository) {
        return new OtpServiceImpl(smsService, mailService, otpSmsExpireSecond, otpMailExpireSecond, codeGeneratorService, userContactReadRepository, userContactWriteRepository);
    }
}
