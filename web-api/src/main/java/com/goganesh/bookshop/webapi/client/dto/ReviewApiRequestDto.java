package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class ReviewApiRequestDto {
    private Integer id;
    @NotBlank
    private String text;
}
