package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.GenreRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.client.dto.BooksPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.exception.NoSuchGenrePageException;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.GenreMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
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
public class GenrePageController {

    private final GenreRepository genreRepository;
    private final BookRestService bookRestService;
    private final BookPageMapper bookMapper;
    private final UserRegisterService userRegisterService;
    private final GenreMapper genreMapper;
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

    @GetMapping("/genres/{slug}")
    public String genrePage(@PathVariable(value = "slug", required = true) String slug,
                            Model model) {
        Genre genre = genreRepository.findBySlug(slug).orElseThrow(() -> new NoSuchGenrePageException("No such genre with slug " + slug));
        model.addAttribute("genrePageDto", genreMapper.toDto(genre));
        model.addAttribute("genreBooks", new BooksPageDto(bookRestService.getPageOfGenreBooks(genre, 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList())));
        return "genres/slug";
    }
}
