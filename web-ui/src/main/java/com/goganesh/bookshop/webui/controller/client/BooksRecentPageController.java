package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.dto.BooksPageDto;
import com.goganesh.bookshop.webui.dto.SearchWordDto;
import com.goganesh.bookshop.webui.dto.UserPageDto;
import com.goganesh.bookshop.webui.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@Builder
public class BooksRecentPageController {

    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user() {
        User user = userRegisterService.getCurrentUser();
        return userMapper.toDto(user);
    }

    @ModelAttribute("recentBooks")
    public BooksPageDto bookList() {
        return new BooksPageDto(bookRestService.getPageOfRecentBooks(LocalDate.now().minusDays(30), LocalDate.now(), 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/books/recent")
    public String booksRecentPage() {
        return "books/recent";
    }
}
