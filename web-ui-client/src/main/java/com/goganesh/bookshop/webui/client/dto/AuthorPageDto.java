package com.goganesh.bookshop.webui.client.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorPageDto {

    private String name;
    private String slug;
    private String description;
    private String photo;
    private long bookCount;
}
