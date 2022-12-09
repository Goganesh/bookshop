package com.goganesh.security.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SendOtpLoginRequest {

    @NotBlank
    private String contact;
    @NotBlank
    private String type;
}
