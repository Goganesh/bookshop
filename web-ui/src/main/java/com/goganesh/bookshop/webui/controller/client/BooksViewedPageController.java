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

import java.util.stream.Collectors;

@Controller
@Builder
public class BooksViewedPageController {

    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user() {
        User user = userRegisterService.getCurrentUser();
        ;
        return userMapper.toDto(user);
    }

    @ModelAttribute("viewedBooks")
    public BooksPageDto bookList() {
        User user = userRegisterService.getCurrentUser();
        return new BooksPageDto(bookRestService.getPageBooksPageByUserAndTypeCode(user, "VIEWED", 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/viewed")
    public String booksPopularPage() {
        return "viewed";
    }
}
