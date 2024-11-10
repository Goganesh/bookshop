package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Data
public class UserApiRequestDto {

    private Integer id;
    @NotBlank
    private String hash;
    @NotBlank
    private String name;
    @Positive
    private String balance;
    @NotBlank
    private String role;
}
