package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BooksDto {
    private int count;
    private List<BookDto> books;

    public BooksDto(List<BookDto> books) {
        this.books = books;
        this.count = books.size();
    }
}
