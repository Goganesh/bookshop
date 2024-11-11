package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.Book2UserTypeRepository;
import com.goganesh.bookshop.webui.dto.BookPageDto;
import com.goganesh.bookshop.webui.dto.SearchWordDto;
import com.goganesh.bookshop.webui.dto.UserPageDto;
import com.goganesh.bookshop.webui.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.mapper.UserMapper;
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
    private final Book2UserRepository book2UserRepository;
    private final Book2UserTypeRepository book2UserTypeRepository;
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
        Book2UserType book2UserType = book2UserTypeRepository.findByCode("KEPT").get();
        return book2UserRepository.findByUserAndBook2UserType(user, book2UserType)
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
