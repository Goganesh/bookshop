package com.goganesh.otp.service.impl;

import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.SmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
@ConditionalOnProperty(name = "com.goganesh.bookshop.sms-service.provider", havingValue = "disable")
public class SmsNoOpServiceImpl implements SmsService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void sendMessage(SendMessage message) {
        logger.info(String.format("Send SMS message - %s", message));
    }
}
