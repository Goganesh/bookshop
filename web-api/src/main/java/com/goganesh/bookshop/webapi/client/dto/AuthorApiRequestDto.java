package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class AuthorApiRequestDto {

    private Integer id;
    @NotBlank
    private String slug;
    @NotBlank
    private String name;
    private String description;
}
