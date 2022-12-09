package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BookChangeDto {
    @NotBlank
    private Integer id;

    private String title;
    private String description;
    private Integer price;
    private Double discount;
}
