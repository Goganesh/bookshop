package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLike, Integer> {
    Optional<BookReviewLike> findByUserAndBookReview(User user, BookReview bookReview);
}
