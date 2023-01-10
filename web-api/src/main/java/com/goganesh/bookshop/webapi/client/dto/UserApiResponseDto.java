package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserApiResponseDto {
    private int id;
    private LocalDateTime regTime;
    private String hash;
    private int balance;
    private String name;
    private boolean isEnabled;
    private String role;
}
