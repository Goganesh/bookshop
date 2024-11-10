package com.goganesh.security.controller.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class RegistrationFormRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
}
