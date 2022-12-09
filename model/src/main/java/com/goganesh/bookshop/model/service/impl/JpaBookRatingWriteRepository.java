package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.repository.JpaBookRatingRepository;
import com.goganesh.bookshop.model.service.BookRatingWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBookRatingWriteRepository implements BookRatingWriteRepository {

    private final JpaBookRatingRepository jpaBookRatingRepository;

    @Override
    public void save(BookRating bookRating) {
        jpaBookRatingRepository.save(bookRating);
    }
}
