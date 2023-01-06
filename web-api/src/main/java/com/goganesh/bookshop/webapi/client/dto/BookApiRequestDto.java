package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BookApiRequestDto {
    private Integer id;
    @NotBlank
    private String slug;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private String pubDate;
    @Positive
    private Integer price;
    @NotNull
    private Double discount;
}
