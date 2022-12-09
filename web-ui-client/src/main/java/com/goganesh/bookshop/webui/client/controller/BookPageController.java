package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.BookFile;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.exception.NoSuchBookPageException;
import com.goganesh.bookshop.webui.client.mapper.BookFileMapper;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.BookReviewMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class BookPageController {

    private final BookReadRepository bookReadRepository;
    private final BookFileReadRepository bookFileReadRepository;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final BookReviewReadRepository bookReviewReadRepository;
    private final Book2UserWriteRepository book2UserWriteRepository;
    private final Book2UserTypeReaRepository book2UserTypeReaRepository;
    private final BookFileMapper bookFileMapper;
    private final BookReviewMapper bookReviewMapper;
    private final UserMapper userMapper;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/books/{slug}")
    public String booksRecentPage(@PathVariable(value = "slug", required = true) String slug,
                                  Model model){
        Book book = bookReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchBookPageException("Where is no book with slug - " + slug));

        User user = userRegisterService.getCurrentUser();
        model.addAttribute("currentUser", userMapper.toDto(user));

        Book2User book2User = Book2User.builder()
                .book(book)
                .user(user)
                .isEnabled(true)
                .book2UserType(book2UserTypeReaRepository.findByCode("VIEWED").get())
                .time(LocalDateTime.now())
                .build();
        book2UserWriteRepository.save(book2User);

        List<BookFile> bookFiles = bookFileReadRepository.findByBook(book);

        model.addAttribute("bookPageDto", bookMapper.toDto(book));
        model.addAttribute("bookFilePageDtos", bookFiles.stream()
                .map(bookFileMapper::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("bookReviewPageDtos", bookReviewReadRepository.findByBook(book)
                .stream()
                .map(bookReviewMapper::toDto)
                .collect(Collectors.toList()));

        return "books/slug";
    }
}
