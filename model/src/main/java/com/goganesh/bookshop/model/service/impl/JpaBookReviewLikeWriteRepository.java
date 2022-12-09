package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.repository.JpaBookReviewLikeRepository;
import com.goganesh.bookshop.model.service.BookReviewLikeWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBookReviewLikeWriteRepository implements BookReviewLikeWriteRepository {

    private final JpaBookReviewLikeRepository jpaBookReviewLikeRepository;

    @Override
    public void save(BookReviewLike bookReviewLike) {
        jpaBookReviewLikeRepository.save(bookReviewLike);
    }
}
