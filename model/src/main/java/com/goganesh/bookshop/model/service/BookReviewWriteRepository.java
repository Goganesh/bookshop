package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BookReview;

public interface BookReviewWriteRepository {

    BookReview save(BookReview bookReview);

    void delete(BookReview bookReview);
}
