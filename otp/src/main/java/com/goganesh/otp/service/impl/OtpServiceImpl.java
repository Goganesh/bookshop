package com.goganesh.otp.service.impl;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.domain.UserContact.ContactType;
import com.goganesh.bookshop.model.repository.UserContactRepository;
import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.CodeGeneratorService;
import com.goganesh.otp.service.MailService;
import com.goganesh.otp.service.OtpService;
import com.goganesh.otp.service.SmsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class OtpServiceImpl implements OtpService {

    private final SmsService smsService;
    private final MailService mailService;
    private final int otpSmsExpireSecond;
    private final int otpMailExpireSecond;
    private final UserContactRepository userContactRepository;
    private final CodeGeneratorService codeGeneratorService;

    public OtpServiceImpl(SmsService smsService,
                          MailService mailService,
                          @Value("${com.goganesh.bookshop.sms-service.otp-expire-second}") int otpSmsExpireSecond,
                          @Value("${com.goganesh.bookshop.mail-service.otp-expire-second}") int otpMailExpireSecond,
                          CodeGeneratorService codeGeneratorService,
                          UserContactRepository userContactRepository) {
        this.smsService = smsService;
        this.mailService = mailService;
        this.otpSmsExpireSecond = otpSmsExpireSecond;
        this.otpMailExpireSecond = otpMailExpireSecond;
        this.codeGeneratorService = codeGeneratorService;
        this.userContactRepository = userContactRepository;
    }

    @Override
    public void sendOtp(UserContact contact) {
        String generatedCode = codeGeneratorService.generateCode();
        String payload = "Your secret code is: " + generatedCode;
        SendMessage sendMessage = SendMessage.builder()
                .contact(contact.getContact())
                .payload(payload)
                .build();

        contact.setCodeTime(LocalDateTime.now());
        contact.setCode(generatedCode);

        switch (contact.getContactType()) {
            case EMAIL -> mailService.sendMessage(sendMessage);
            case PHONE -> smsService.sendMessage(sendMessage);
            default ->
                    throw new IllegalArgumentException(String.format("Can`t send otp for contact type %s", contact.getContactType()));
        }

        userContactRepository.save(contact);
    }

    @Override
    public Optional<UserContact> verifyCode(String contact, ContactType contactType, String code) {
        UserContact userContact = userContactRepository.findByContactAndContactTypeAndCode(contact, contactType, code)
                .orElse(null);

        if (Objects.isNull(userContact) || otpCodeIsExpired(userContact)) {
            userContact = null;
        }

        return Optional.ofNullable(userContact);
    }

    private boolean otpCodeIsExpired(UserContact userContact) {
        return switch (userContact.getContactType()) {
            case EMAIL -> userContact.getCodeTime().plusSeconds(otpMailExpireSecond).isBefore(LocalDateTime.now());
            case PHONE -> userContact.getCodeTime().plusSeconds(otpSmsExpireSecond).isBefore(LocalDateTime.now());
            default ->
                    throw new IllegalArgumentException(String.format("Can`t verify otp for contact type %s", userContact.getContactType()));
        };
    }
}
