package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.common.aop.annotation.LogExecutionTime;
import com.goganesh.bookshop.common.aop.annotation.Logger;
import com.goganesh.bookshop.common.service.BookActionService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.webapi.client.dto.ChangeBookStatusDto;
import com.goganesh.bookshop.webapi.client.dto.ResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ShopApiController {

    private final UserRegisterService userRegisterService;
    private final BookActionService bookActionService;
    private final BookReadRepository bookReadRepository;

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/books/changeBookStatus")
    @LogExecutionTime
    @Logger
    public ResponseDto changeBookStatus(@RequestBody ChangeBookStatusDto changeBookStatusDto) {
        String action = changeBookStatusDto.getStatus();
        User user = userRegisterService.getCurrentUser();
        List<Book> books = changeBookStatusDto.getBooksIds()
                .stream()
                .map(slug -> bookReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchBookException("No such book with slug - " + slug)))
                .collect(Collectors.toList());

        bookActionService.execute(action, user, books);

        return ResponseDto.builder()
                .result(true)
                .build();
    }
}
