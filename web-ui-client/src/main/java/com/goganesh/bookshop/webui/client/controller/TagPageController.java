package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.TagReadRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.exception.NoSuchTagPageException;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.TagMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.stream.Collectors;

@Controller
@Builder
public class TagPageController {

    private final TagReadRepository tagReadRepository;
    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final TagMapper tagMapper;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user() {
        User user = userRegisterService.getCurrentUser();
        return userMapper.toDto(user);
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/tags/{slug}")
    public String authorsPage(@PathVariable(value = "slug", required = true) String slug,
                              Model model) {
        Tag tag = tagReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchTagPageException("No such tag with slag - " + slug));
        model.addAttribute("tagPageDto", tagMapper.toDto(tag));
        model.addAttribute("tagBooks", new BooksPageDto(bookRestService.getPageOfTagBooks(tag, 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList())));
        return "tags/index";
    }
}
