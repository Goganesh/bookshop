package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ReviewApiRequestDto {
    private Integer id;
    @NotBlank
    private String text;
}
