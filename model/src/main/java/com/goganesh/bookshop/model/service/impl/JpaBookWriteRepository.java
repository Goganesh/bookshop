package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.repository.JpaBookRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBookWriteRepository implements BookWriteRepository {

    private final JpaBookRepository jpaBookRepository;

    @Override
    public Book save(Book book) {
        return jpaBookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        jpaBookRepository.deleteById(id);
    }

    @Override
    public void delete(Book book) {
        jpaBookRepository.delete(book);
    }
}
