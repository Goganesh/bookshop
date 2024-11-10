package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.GenreRepository;
import com.goganesh.bookshop.webui.client.dto.GenrePageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.GenreMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class GenresPageController {

    private final GenreRepository genreRepository;
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

    @ModelAttribute("genres")
    public List<GenrePageDto> genresList() {
        return genreRepository.findByParentIsNull()
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }
}
