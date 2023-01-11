package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.model.service.Book2UserTypeReaRepository;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.bookshop.webui.client.dto.BookPageDto;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class PostponedPageController {

    private final UserRegisterService userRegisterService;
    private final Book2UserReadRepository book2UserReadRepository;
    private final Book2UserTypeReaRepository book2UserTypeReaRepository;
    private final BookPageMapper bookMapper;
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

    @ModelAttribute("keptBooks")
    public List<BookPageDto> keptBooks() {
        User user = userRegisterService.getCurrentUser();
        Book2UserType book2UserType = book2UserTypeReaRepository.findByCode("KEPT").get();
        return book2UserReadRepository.findByUserAndBook2UserType(user, book2UserType)
                .stream()
                .map(Book2User::getBook)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/postponed")
    public String postponedPage() {
        return "postponed";
    }

}
