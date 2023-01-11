package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewApiRequestDto {
    private Integer id;
    @NotBlank
    private String text;
}
