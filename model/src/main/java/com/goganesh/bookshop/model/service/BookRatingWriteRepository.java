package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BookRating;

public interface BookRatingWriteRepository {

    void save(BookRating bookRating);
}
