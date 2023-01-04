package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

@Data
public class GenreApiResponseDto {
    private int id;
    private int parentId;
    private String slug;
    private String name;
}
