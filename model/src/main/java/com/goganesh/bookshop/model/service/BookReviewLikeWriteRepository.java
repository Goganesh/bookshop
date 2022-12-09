package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BookReviewLike;

public interface BookReviewLikeWriteRepository {
    void save(BookReviewLike bookReviewLike);
}
