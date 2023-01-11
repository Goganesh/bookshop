package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.model.domain.BookReviewLike;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.webapi.client.dto.*;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.bookshop.webapi.client.exception.NoSuchReviewException;
import com.goganesh.bookshop.webapi.client.mapper.ReviewApiMapper;
import com.goganesh.bookshop.webapi.client.service.ReviewRestService;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ReviewController {

    private final BookReviewLikeReadRepository bookReviewLikeReadRepository;
    private final BookReviewLikeWriteRepository bookReviewLikeWriteRepository;
    private final BookReadRepository bookReadRepository;
    private final UserRegisterService userRegisterService;

    private final ReviewRestService reviewRestService;
    private final ReviewApiMapper reviewApiMapper;


    @GetMapping("/reviews")
    public Page<ReviewApiResponseDto> getReviews(@PageableDefault(value = 20) Pageable pageable) {
        return reviewRestService.findAll(pageable).map(reviewApiMapper::toDto);
    }

    @GetMapping("/reviews/{id}")
    public ReviewApiResponseDto getBook(@PathVariable("id") Integer id) {
        BookReview bookReview = reviewRestService.findById(id).orElseThrow(() -> new NoSuchReviewException("No such review with id " + id));
        return reviewApiMapper.toDto(bookReview);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        BookReview bookReview = reviewRestService.findById(id).orElseThrow(() -> new NoSuchReviewException("No such review with id " + id));
        reviewRestService.delete(bookReview);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reviews")
    public ReviewApiResponseDto postBook(@Validated @RequestBody ReviewApiRequestDto reviewApiRequestDto) {
        BookReview existedReview = null;

        if (reviewApiRequestDto.getId() == -1 || Objects.isNull(reviewApiRequestDto.getId())) {
            reviewApiRequestDto.setId(null);
        } else {
            existedReview = reviewRestService.findById(reviewApiRequestDto.getId())
                    .orElseThrow(() -> new NoSuchReviewException("No such review with id " + reviewApiRequestDto.getId()));
        }

        BookReview bookReview = reviewApiMapper.toModel(reviewApiRequestDto);
        if (Objects.nonNull(existedReview)) {
            bookReview.setBook(existedReview.getBook());
            bookReview.setBookReviewLikes(existedReview.getBookReviewLikes());
            bookReview.setUser(existedReview.getUser());
            bookReview.setTime(existedReview.getTime());
        }

        bookReview = reviewRestService.save(bookReview);

        return reviewApiMapper.toDto(bookReview);
    }

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

        reviewRestService.save(bookReview);

        return ResponseDto.builder()
                .result(true)
                .build();
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/rateBookReview")
    public ResponseDto rateBookReview(@Valid @RequestBody BookReviewRateDto bookReviewRateDto) {
        User user = userRegisterService.getCurrentUser();
        int reviewId = bookReviewRateDto.getReviewId();
        BookReview bookReview = reviewRestService.findById(reviewId).orElseThrow(
                () -> new NoSuchReviewException("No such book review with id - " + reviewId));
        byte value = (byte) bookReviewRateDto.getValue();

        BookReviewLike bookReviewLike = bookReviewLikeReadRepository.findByUserAndBookReview(user, bookReview).orElse(null);
        if (Objects.isNull(bookReviewLike)) {
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
