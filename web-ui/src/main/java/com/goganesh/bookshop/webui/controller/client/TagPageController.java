package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.TagRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.bookshop.webui.dto.BooksPageDto;
import com.goganesh.bookshop.webui.dto.SearchWordDto;
import com.goganesh.bookshop.webui.dto.UserPageDto;
import com.goganesh.bookshop.webui.exception.NoSuchTagPageException;
import com.goganesh.bookshop.webui.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.mapper.TagMapper;
import com.goganesh.bookshop.webui.mapper.UserMapper;
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

    private final TagRepository tagRepository;
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
        Tag tag = tagRepository.findBySlug(slug).orElseThrow(() -> new NoSuchTagPageException("No such tag with slag - " + slug));
        model.addAttribute("tagPageDto", tagMapper.toDto(tag));
        model.addAttribute("tagBooks", new BooksPageDto(bookRestService.getPageOfTagBooks(tag, 0, 20).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList())));
        return "tags/index";
    }
}
