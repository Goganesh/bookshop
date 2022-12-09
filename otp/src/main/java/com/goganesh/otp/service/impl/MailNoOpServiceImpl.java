package com.goganesh.otp.service.impl;

import com.goganesh.otp.model.SendMessage;
import com.goganesh.otp.service.MailService;

import java.util.logging.Logger;


public class MailNoOpServiceImpl implements MailService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void sendMessage(SendMessage message) {
        logger.info(String.format("Send MAIL message - %s", message));
    }
}
