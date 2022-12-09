package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.domain.User;

import java.util.Optional;

public interface BookReviewLikeReadRepository {
    Optional<BookReviewLike> findByUserAndBookReview(User user, BookReview bookReview);
}
