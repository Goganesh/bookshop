package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.repository.JpaBookRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBookWriteRepository implements BookWriteRepository {

    private final JpaBookRepository jpaBookRepository;

    @Override
    public void save(Book book) {
        jpaBookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        jpaBookRepository.deleteById(id);
    }
}
