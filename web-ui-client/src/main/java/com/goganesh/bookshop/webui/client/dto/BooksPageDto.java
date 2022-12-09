package com.goganesh.bookshop.webui.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BooksPageDto {
    private int count;
    private List<BookPageDto> books;

    public BooksPageDto(List<BookPageDto> books) {
        this.books = books;
        this.count = books.size();
    }
}
