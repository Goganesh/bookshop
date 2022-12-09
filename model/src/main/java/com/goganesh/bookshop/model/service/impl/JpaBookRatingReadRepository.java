package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaBookRatingRepository;
import com.goganesh.bookshop.model.service.BookRatingReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaBookRatingReadRepository implements BookRatingReadRepository {

    private final JpaBookRatingRepository jpaBookRatingRepository;

    @Override
    public Optional<BookRating> findByUserAndBook(User user, Book book) {
        return jpaBookRatingRepository.findByUserAndBook(user, book);
    }

    @Override
    public List<BookRating> findByBook(Book book) {
        return jpaBookRatingRepository.findByBook(book);
    }
}
