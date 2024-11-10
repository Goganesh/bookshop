package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.BookRatingRepository;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webapi.client.dto.BookRateDto;
import com.goganesh.bookshop.webapi.client.dto.ResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.security.service.UserRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class RateController {

    private final BookRatingRepository bookRatingRepository;
    private final BookRepository bookRepository;
    private final UserRegisterService userRegisterService;

    public RateController(BookRatingRepository bookRatingRepository, BookRepository bookRepository, UserRegisterService userRegisterService) {
        this.bookRatingRepository = bookRatingRepository;
        this.bookRepository = bookRepository;
        this.userRegisterService = userRegisterService;
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/rateBook")
    public ResponseDto rateBook(@Valid @RequestBody BookRateDto bookRateDto) {

        String slug = bookRateDto.getBookId();

        Book book = bookRepository.findBySlug(slug).orElseThrow(() -> new NoSuchBookException("Where is no book with slug - " + slug));
        User user = userRegisterService.getCurrentUser();
        int rating = bookRateDto.getValue();

        BookRating bookRating = bookRatingRepository.findByUserAndBook(user, book).orElse(null);
        if (Objects.isNull(bookRating)) {
            bookRating = BookRating.builder()
                    .user(user)
                    .book(book)
                    .build();
        }

        bookRating.setRating(rating);
        bookRating.setTime(LocalDateTime.now());

        bookRatingRepository.save(bookRating);

        return ResponseDto.builder()
                .result(true)
                .build();
    }
}
