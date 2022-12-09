package com.goganesh.otp.service;

import com.goganesh.otp.model.SendMessage;

public interface MailService {

    void sendMessage(SendMessage message);
}
