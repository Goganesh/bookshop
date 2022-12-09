package com.goganesh.otp.service;

import com.goganesh.otp.model.SendMessage;

public interface SmsService {

    void sendMessage(SendMessage message);
}
