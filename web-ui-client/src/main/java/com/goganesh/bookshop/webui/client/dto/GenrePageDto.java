package com.goganesh.bookshop.webui.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenrePageDto {
    private String name;
    private String slug;
    private long bookCount;
    private List<GenrePageDto> childGenrePageDtos;
}
