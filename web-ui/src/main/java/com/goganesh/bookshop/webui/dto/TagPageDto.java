package com.goganesh.bookshop.webui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPageDto {
    private String slug;
    private String name;
    private long count;
    private String size;
}
