package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.webapi.client.dto.BookReviewDto;
import com.goganesh.bookshop.webapi.client.dto.BookReviewRateDto;
import com.goganesh.bookshop.webapi.client.dto.ResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookReviewException;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ReviewController {

    private final BookReviewWriteRepository bookReviewWriteRepository;
    private final BookReviewReadRepository bookReviewReadRepository;
    private final BookReviewLikeReadRepository bookReviewLikeReadRepository;
    private final BookReviewLikeWriteRepository bookReviewLikeWriteRepository;
    private final BookReadRepository bookReadRepository;
    private final UserRegisterService userRegisterService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/bookReview")
    public ResponseDto reviewBook(@Valid @RequestBody BookReviewDto bookReviewDto) {
        User user = userRegisterService.getCurrentUser();

        String slug = bookReviewDto.getBookId();
        Book book = bookReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchBookException("No such book with slug - " + slug));

        String text = bookReviewDto.getText();

        BookReview bookReview = BookReview.builder()
                .book(book)
                .user(user)
                .text(text)
                .time(LocalDateTime.now())
                .build();

        bookReviewWriteRepository.save(bookReview);

        return ResponseDto.builder()
                .result(true)
                .build();
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/rateBookReview")
    public ResponseDto rateBookReview(@Valid @RequestBody BookReviewRateDto bookReviewRateDto) {
        User user = userRegisterService.getCurrentUser();
        int reviewId = bookReviewRateDto.getReviewId();
        BookReview bookReview = bookReviewReadRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchBookReviewException("No such book review with id - " + reviewId));
        byte value = (byte) bookReviewRateDto.getValue();

        BookReviewLike bookReviewLike = bookReviewLikeReadRepository.findByUserAndBookReview(user, bookReview).orElse(null);
        if (Objects.isNull(bookReviewLike)){
            bookReviewLike = BookReviewLike.builder()
                    .user(user)
                    .bookReview(bookReview)
                    .build();
        }

        bookReviewLike.setValue(value);
        bookReviewLike.setTime(LocalDateTime.now());

        bookReviewLikeWriteRepository.save(bookReviewLike);

        return ResponseDto.builder()
                .result(true)
                .build();
    }

}
