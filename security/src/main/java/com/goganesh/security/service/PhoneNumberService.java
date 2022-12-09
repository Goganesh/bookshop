package com.goganesh.security.service;

public interface PhoneNumberService {

    String formatPhoneNumber(String phoneNumber);

    boolean isValidPhoneNumber(String phoneNumber);
}
