package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookDto {

    private String slug;
    private String title;
    private String description;
    private String image;
    private String authors;
    private double discount;
    private int rating;
    private boolean isBestseller;
    private String status;
    private int price;
    private int discountPrice;
}
