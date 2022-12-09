package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.repository.JpaBookReviewRepository;
import com.goganesh.bookshop.model.service.BookReviewReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaBookReviewReadRepository implements BookReviewReadRepository {

    private final JpaBookReviewRepository jpaBookReviewRepository;

    @Override
    public List<BookReview> findByBook(Book book) {
        return jpaBookReviewRepository.findByBook(book);
    }

    @Override
    public Optional<BookReview> findById(int id) {
        return jpaBookReviewRepository.findById(id);
    }
}
