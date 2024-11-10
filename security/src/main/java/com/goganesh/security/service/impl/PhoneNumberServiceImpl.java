package com.goganesh.security.service.impl;

import com.goganesh.security.service.PhoneNumberService;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Override
    public String formatPhoneNumber(String phoneNumber) {
        String correctNumber = phoneNumber.replaceAll("\\D", "");
        if (correctNumber.length() == 11)
            correctNumber = correctNumber.substring(1, 11);
        return correctNumber;
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{10}$";
        return phoneNumber.matches(regex);
    }
}
