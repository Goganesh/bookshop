package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.webapi.client.dto.BookApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.BookApiResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.bookshop.webapi.client.mapper.BookApiMapper;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@PreAuthorize("hasAnyRole('ADMIN')")
//todo
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookRestService bookRestService;
    private final BookApiMapper bookApiMapper;

    public BookController(BookRestService bookRestService, BookApiMapper bookApiMapper) {
        this.bookRestService = bookRestService;
        this.bookApiMapper = bookApiMapper;
    }

    @GetMapping()
    public Page<BookApiResponseDto> getBooks(@PageableDefault(value = 20) Pageable pageable) {
        return bookRestService.findAll(pageable).map(bookApiMapper::toDto);
    }

    @GetMapping("/{id}")
    public BookApiResponseDto getBook(@PathVariable("id") Integer id) {
        Book book = bookRestService.findById(id)
                .orElseThrow(() -> new NoSuchBookException(NoSuchBookException.NO_BOOK_ID + id));
        return bookApiMapper.toDto(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        Book book = bookRestService.findById(id)
                .orElseThrow(() -> new NoSuchBookException(NoSuchBookException.NO_BOOK_ID + id));
        bookRestService.delete(book);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public BookApiResponseDto postBook(@Validated @RequestBody BookApiRequestDto bookApiRequestDto) {
        Book existedBook = null;

        if (bookApiRequestDto.getId() == -1 || Objects.isNull(bookApiRequestDto.getId())) {
            bookApiRequestDto.setId(null);
        } else {
            existedBook = bookRestService.findById(bookApiRequestDto.getId())
                    .orElseThrow(() -> new NoSuchBookException(NoSuchBookException.NO_BOOK_ID + bookApiRequestDto.getId()));
        }

        Book book = bookApiMapper.toModel(bookApiRequestDto);
        if (Objects.nonNull(existedBook)) {
            book.setImage(existedBook.getImage());
            book.setBestseller(existedBook.isBestseller());
            book.setPopularity(existedBook.getPopularity());
        }

        book = bookRestService.save(book);

        return bookApiMapper.toDto(book);
    }
}
