package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.repository.JpaBookReviewRepository;
import com.goganesh.bookshop.model.service.BookReviewWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBookReviewWriteRepository implements BookReviewWriteRepository {

    private final JpaBookReviewRepository jpaBookReviewRepository;

    @Override
    public void save(BookReview bookReview) {
        jpaBookReviewRepository.save(bookReview);
    }
}
