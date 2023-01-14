package com.goganesh.otp.service.impl;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.domain.UserContact.ContactType;
import com.goganesh.bookshop.model.service.UserContactReadRepository;
import com.goganesh.bookshop.model.service.UserContactWriteRepository;
import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.CodeGeneratorService;
import com.goganesh.otp.service.MailService;
import com.goganesh.otp.service.OtpService;
import com.goganesh.otp.service.SmsService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class OtpServiceImpl implements OtpService {

    private final SmsService smsService;
    private final MailService mailService;
    private final int otpSmsExpireSecond;
    private final int otpMailExpireSecond;
    private final UserContactReadRepository userContactReadRepository;
    private final UserContactWriteRepository userContactWriteRepository;
    private final CodeGeneratorService codeGeneratorService;

    public OtpServiceImpl(SmsService smsService,
                          MailService mailService,
                          int otpSmsExpireSecond,
                          int otpMailExpireSecond,
                          CodeGeneratorService codeGeneratorService,
                          UserContactReadRepository userContactReadRepository,
                          UserContactWriteRepository userContactWriteRepository) {
        this.smsService = smsService;
        this.mailService = mailService;
        this.otpSmsExpireSecond = otpSmsExpireSecond;
        this.otpMailExpireSecond = otpMailExpireSecond;
        this.codeGeneratorService = codeGeneratorService;
        this.userContactReadRepository = userContactReadRepository;
        this.userContactWriteRepository = userContactWriteRepository;
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
            case EMAIL:
                mailService.sendMessage(sendMessage);
                break;
            case PHONE:
                smsService.sendMessage(sendMessage);
                break;
            default:
                throw new IllegalArgumentException(String.format("Can`t send otp for contact type %s", contact.getContactType()));
        }

        userContactWriteRepository.save(contact);
    }

    @Override
    public Optional<UserContact> verifyCode(String contact, ContactType contactType, String code) {
        UserContact userContact = userContactReadRepository.findByContactAndContactTypeAndCode(contact, contactType, code)
                .orElse(null);

        if (Objects.isNull(userContact) || otpCodeIsExpired(userContact)) {
            userContact = null;
        }

        return Optional.ofNullable(userContact);
    }

    private boolean otpCodeIsExpired(UserContact userContact) {
        boolean result;
        switch (userContact.getContactType()) {
            case EMAIL:
                result = userContact.getCodeTime().plusSeconds(otpMailExpireSecond).isBefore(LocalDateTime.now());
                break;
            case PHONE:
                result = userContact.getCodeTime().plusSeconds(otpSmsExpireSecond).isBefore(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException(String.format("Can`t verify otp for contact type %s", userContact.getContactType()));
        }

        return result;
    }
}
