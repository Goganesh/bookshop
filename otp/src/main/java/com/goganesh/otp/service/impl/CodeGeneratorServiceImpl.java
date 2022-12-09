package com.goganesh.otp.service.impl;

import com.goganesh.otp.service.CodeGeneratorService;

import java.util.Random;

public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final int symbols;

    public CodeGeneratorServiceImpl(int symbols) {
        this.symbols = symbols;
    }

    @Override
    public String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < symbols) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }
}
