package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRestService {

    Page<BookReview> findAll(Pageable pageable);

    Optional<BookReview> findById(Integer id);

    void delete(BookReview bookReview);

    BookReview save(BookReview bookReview);
}
