package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BookReview;

public interface BookReviewWriteRepository {

    void save(BookReview bookReview);
}
