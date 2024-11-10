package com.goganesh.otp.service.impl;

import com.goganesh.otp.service.CodeGeneratorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final int symbols;

    public CodeGeneratorServiceImpl(@Value("${com.goganesh.bookshop.code-generator.symbols}") int symbols) {
        this.symbols = symbols;
    }

    @SneakyThrows
    @Override
    public String generateCode() {
        Random random = SecureRandom.getInstanceStrong();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < symbols) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }
}
