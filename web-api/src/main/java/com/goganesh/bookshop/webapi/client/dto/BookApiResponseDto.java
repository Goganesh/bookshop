package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookApiResponseDto {
    private Integer id;
    private LocalDate pubDate;
    private Boolean isBestseller;
    private String slug;
    private String title;
    private String description;
    private String image;
    private Integer price;
    private Double discount;
    private Double popularity;
}
