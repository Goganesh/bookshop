package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaBookReviewLikeRepository;
import com.goganesh.bookshop.model.service.BookReviewLikeReadRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class JpaBookReviewLikeReadRepository implements BookReviewLikeReadRepository {

    private final JpaBookReviewLikeRepository jpaBookReviewLikeRepository;

    @Override
    public Optional<BookReviewLike> findByUserAndBookReview(User user, BookReview bookReview) {
        return jpaBookReviewLikeRepository.findByUserAndBookReview(user, bookReview);
    }
}
