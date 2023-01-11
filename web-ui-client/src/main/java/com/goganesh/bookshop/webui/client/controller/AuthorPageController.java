package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.AuthorReadRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.AuthorPageDto;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.exception.NoSuchAuthorPageException;
import com.goganesh.bookshop.webui.client.mapper.AuthorMapper;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@Builder
public class AuthorPageController {

    private final AuthorReadRepository authorReadRepository;
    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final AuthorMapper authorMapper;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserPageDto user() {
        User user = userRegisterService.getCurrentUser();
        return userMapper.toDto(user);
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return new TreeMap(authorReadRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.groupingBy((AuthorPageDto a) -> a.getName().substring(0, 1))));
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/authors/{slug}")
    public String authorPage(@PathVariable(value = "slug", required = true) String slug,
                             Model model) {
        Author author = authorReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchAuthorPageException("No such author with slug " + slug));
        model.addAttribute("authorPageDto", authorMapper.toDto(author));
        model.addAttribute("authorBooks", new BooksPageDto(bookRestService.getPageOfAuthorBooks(author, 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList())));

        return "authors/slug";
    }
}
