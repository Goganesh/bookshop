package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.AuthorRepository;
import com.goganesh.bookshop.webui.dto.AuthorPageDto;
import com.goganesh.bookshop.webui.dto.SearchWordDto;
import com.goganesh.bookshop.webui.dto.UserPageDto;
import com.goganesh.bookshop.webui.mapper.AuthorMapper;
import com.goganesh.bookshop.webui.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@Builder
public class AuthorsPageController {

    private final AuthorRepository authorRepository;
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
        return new TreeMap(authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.groupingBy((AuthorPageDto a) -> a.getName().substring(0, 1))));
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "authors/index";
    }
}
