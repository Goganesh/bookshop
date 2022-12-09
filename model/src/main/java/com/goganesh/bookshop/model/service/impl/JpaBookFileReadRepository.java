package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookFile;
import com.goganesh.bookshop.model.repository.JpaBookFileRepository;
import com.goganesh.bookshop.model.service.BookFileReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class JpaBookFileReadRepository implements BookFileReadRepository {

    private final JpaBookFileRepository jpaBookFileRepository;

    @Override
    public List<BookFile> findByBook(Book book) {
        return jpaBookFileRepository.findByBook(book);
    }
}
