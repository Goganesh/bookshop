package com.goganesh.bookshop.common.service;

import com.goganesh.bookshop.model.domain.Book;

import java.util.List;

public interface BookPopularityService {

    void calculateBookPopularity(Book book);
    void calculateBooksPopularity(List<Book> books);
}