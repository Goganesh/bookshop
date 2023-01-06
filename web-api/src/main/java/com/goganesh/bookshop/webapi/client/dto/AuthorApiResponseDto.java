package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

@Data
public class AuthorApiResponseDto {
    private int id;
    private String slug;
    private String name;
    private String description;
    private String photo;
}
