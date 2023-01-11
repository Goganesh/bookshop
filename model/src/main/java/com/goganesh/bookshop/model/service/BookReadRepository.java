package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface BookReadRepository {

    long countBooksByAuthor(Author author);

    long countBooksByGenre(Genre genre);

    long countBooksByTag(Tag tag);

    Page<Book> findByGenre(Genre genre, Pageable nextPage);

    Page<Book> findByAuthor(Author author, Pageable nextPage);

    Page<Book> findByTag(Tag tag, Pageable nextPage);

    Page<Book> findByUserAndTypeCode(User user, String typeCode, Pageable nextPage);

    Optional<Book> findBySlug(String slug);

    Page<Book> findAll(Pageable nextPage);

    Page<Book> findByTitleContaining(String bookTitle, Pageable nextPage);

    Page<Book> findByPubDateBetween(LocalDate pubDateStart, LocalDate pubDateFinish, Pageable nextPage);

    Optional<Book> findById(Integer id);
}
