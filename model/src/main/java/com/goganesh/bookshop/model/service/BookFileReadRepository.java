package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookFile;

import java.util.List;

public interface BookFileReadRepository {

    List<BookFile> findByBook(Book book);
}
