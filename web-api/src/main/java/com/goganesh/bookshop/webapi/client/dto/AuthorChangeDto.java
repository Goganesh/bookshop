package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthorChangeDto {
    @NotBlank
    private Integer id;

    private String name;
    private String description;
}
