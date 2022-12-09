package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.TagReadRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.TagPageDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.TagMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class IndexPageController {

    private final BookRestService bookRestService;
    private final TagReadRepository tagReadRepository;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final TagMapper tagMapper;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user(){
        User user = userRegisterService.getCurrentUser();
        return userMapper.toDto(user);
    }

    @ModelAttribute("recommendedBooks")
    public BooksPageDto recommendedBooks(){
        return new BooksPageDto(bookRestService.getPageOfRecommendedBooks(1, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("recentBooks")
    public BooksPageDto recentBooks(){
        return new BooksPageDto(bookRestService.getPageOfRecentBooks(LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 8), 1, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("popularBooks")
    public BooksPageDto popularBooks(){
        return new BooksPageDto(bookRestService.getPageOfPopularBooks(0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ModelAttribute("tags")
    public List<TagPageDto> tags(){
        return tagReadRepository.findAll()
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

}
