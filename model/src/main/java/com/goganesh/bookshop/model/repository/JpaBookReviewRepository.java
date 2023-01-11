package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBookReviewRepository extends JpaRepository<BookReview, Integer> {

    List<BookReview> findByBook(Book book);

    List<BookReview> findAllByBook(Book book);

}
