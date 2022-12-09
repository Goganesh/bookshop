package com.goganesh.bookshop.model.service.impl;


import com.goganesh.bookshop.model.domain.*;
import com.goganesh.bookshop.model.repository.JpaBookRepository;
import com.goganesh.bookshop.model.service.BookReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class JpaBookReadRepository implements BookReadRepository {

    private final JpaBookRepository jpaBookRepository;

    @Override
    public Optional<Book> findById(Integer id) {
        return jpaBookRepository.findById(id);
    }

    @Override
    public Page<Book> findAll(Pageable nextPage) {
        return jpaBookRepository.findAll(nextPage);
    }

    @Override
    public long countBooksByAuthor(Author author) {
        return jpaBookRepository.countBooksByAuthor(author);
    }

    @Override
    public long countBooksByGenre(Genre genre) {
        return jpaBookRepository.countBooksByGenre(genre);
    }

    @Override
    public long countBooksByTag(Tag tag) {
        return jpaBookRepository.countBooksByTag(tag);
    }

    @Override
    public Page<Book> findByGenre(Genre genre, Pageable nextPage) {
        return jpaBookRepository.findByGenre(genre, nextPage);
    }

    @Override
    public Page<Book> findByAuthor(Author author, Pageable nextPage) {
        return jpaBookRepository.findByAuthor(author, nextPage);
    }

    @Override
    public Page<Book> findByTag(Tag tag, Pageable nextPage) {
        return jpaBookRepository.findByTag(tag, nextPage);
    }

    @Override
    public Page<Book> findByUserAndTypeCode(User user, String typeCode, Pageable nextPage) {
        return jpaBookRepository.findByUserAndTypeCode(user, typeCode, nextPage);
    }

    @Override
    public Optional<Book> findBySlug(String slug) {
        return jpaBookRepository.findBySlug(slug);
    }

    @Override
    public Page<Book> findByTitleContaining(String bookTitle, Pageable nextPage) {
        return jpaBookRepository.findByTitleContaining(bookTitle, nextPage);
    }

    @Override
    public Page<Book> findByPubDateBetween(LocalDate pubDateStart, LocalDate pubDateFinish, Pageable nextPage) {
        return jpaBookRepository.findByPubDateBetween(pubDateStart, pubDateFinish, nextPage);
    }
}
