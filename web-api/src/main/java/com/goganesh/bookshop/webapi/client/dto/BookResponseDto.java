package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponseDto {

    private Integer id;
    private LocalDate pubDate;
    private boolean isBestseller;
    private String slug;
    private String title;
    private String image;
    private String description;
    private int price;
    private double discount;
    private double popularity;
}
