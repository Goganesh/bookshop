package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.service.BookReviewReadRepository;
import com.goganesh.bookshop.model.service.BookReviewWriteRepository;
import com.goganesh.bookshop.webapi.client.service.ReviewRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@AllArgsConstructor
public class ReviewRestServiceImpl implements ReviewRestService {

    private final BookReviewWriteRepository bookReviewWriteRepository;
    private final BookReviewReadRepository bookReviewReadRepository;

    @Override
    public Page<BookReview> findAll(Pageable pageable) {
        return bookReviewReadRepository.findAll(pageable);
    }

    @Override
    public Optional<BookReview> findById(Integer id) {
        return bookReviewReadRepository.findById(id);
    }

    @Override
    public void delete(BookReview bookReview) {
        bookReviewWriteRepository.delete(bookReview);
    }

    @Override
    public BookReview save(BookReview bookReview) {
        return bookReviewWriteRepository.save(bookReview);
    }
}
