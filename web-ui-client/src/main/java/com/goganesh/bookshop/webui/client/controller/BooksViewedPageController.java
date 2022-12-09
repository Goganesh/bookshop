package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.stream.Collectors;

@Controller
@Builder
public class BooksViewedPageController {

    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user(){
        User user = userRegisterService.getCurrentUser();;
        return userMapper.toDto(user);
    }

    @ModelAttribute("viewedBooks")
    public BooksPageDto bookList(){
        User user = userRegisterService.getCurrentUser();
        return new BooksPageDto(bookRestService.getPageBooksPageByUserAndTypeCode(user, "VIEWED", 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/viewed")
    public String booksPopularPage(){
        return "viewed";
    }
}
