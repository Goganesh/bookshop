package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponseDto {
    private Integer id;
    private String photo;
    private String slug;
    private String name;
    private String description;
}
