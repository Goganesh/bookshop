package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookReviewReadRepository {

    Page<BookReview> findAll(Pageable pageable);

    List<BookReview> findByBook(Book book);

    Optional<BookReview> findById(int id);

}
