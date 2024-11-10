package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Data
public class GenreApiRequestDto {

    private Integer id;
    @Positive
    private Integer parentId;
    @NotBlank
    private String slug;
    @NotBlank
    private String name;
}
