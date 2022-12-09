package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.stream.Collectors;

@Controller
@Builder
public class MyPageController {
    private final UserRegisterService userRegisterService;
    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserMapper userMapper;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/my")
    public String handleMy(Model model) {
        User user = userRegisterService.getCurrentUser();
        model.addAttribute("currentUser", userMapper.toDto(user));

        model.addAttribute("userBooks", new BooksPageDto(
                bookRestService.getPageBooksPageByUserAndTypeCode(user, "PAID", 0, 20).getContent()
                        .stream()
                        .map(bookMapper::toDto)
                        .collect(Collectors.toList())));

        return "my";
    }
}
