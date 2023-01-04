package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
