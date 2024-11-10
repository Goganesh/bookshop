package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.repository.BookReviewRepository;
import com.goganesh.bookshop.webapi.client.service.ReviewRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReviewRestServiceImpl implements ReviewRestService {

    private final BookReviewRepository bookReviewRepository;

    public ReviewRestServiceImpl(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Override
    public Page<BookReview> findAll(Pageable pageable) {
        return bookReviewRepository.findAll(pageable);
    }

    @Override
    public Optional<BookReview> findById(Integer id) {
        return bookReviewRepository.findById(id);
    }

    @Override
    public void delete(BookReview bookReview) {
        bookReviewRepository.delete(bookReview);
    }

    @Override
    public BookReview save(BookReview bookReview) {
        return bookReviewRepository.save(bookReview);
    }
}
