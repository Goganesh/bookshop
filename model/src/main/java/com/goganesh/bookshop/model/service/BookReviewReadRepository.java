package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;

import java.util.List;
import java.util.Optional;

public interface BookReviewReadRepository {

    List<BookReview> findByBook(Book book);

    Optional<BookReview> findById(int id);

}
