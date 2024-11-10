package com.goganesh.security.controller.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class ApproveContactRequest {

    @NotBlank
    private String contact;
    @NotBlank
    private String code;
    @NotBlank
    private String type;
}
