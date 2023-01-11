package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface BookRatingReadRepository {

    Optional<BookRating> findByUserAndBook(User user, Book book);

    List<BookRating> findByBook(Book book);
}
