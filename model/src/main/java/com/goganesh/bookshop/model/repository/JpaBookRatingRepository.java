package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBookRatingRepository extends JpaRepository<BookRating, Integer> {

    Optional<BookRating> findByUserAndBook(User user, Book book);

    List<BookRating> findByBook(Book book);
}
