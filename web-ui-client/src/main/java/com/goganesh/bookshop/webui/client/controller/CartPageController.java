package com.goganesh.bookshop.webui.client.controller;

import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.model.service.Book2UserTypeReaRepository;
import com.goganesh.bookshop.webui.client.dto.BookPageDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.BookPageMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class CartPageController {

    private final Book2UserTypeReaRepository book2UserTypeReaRepository;
    private final UserRegisterService userRegisterService;
    private final Book2UserReadRepository book2UserReadRepository;
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

    @GetMapping("/cart")
    public String handleCartRequest(Model model) {
        User user = userRegisterService.getCurrentUser();
        Book2UserType cartType = book2UserTypeReaRepository.findByCode("CART").get();

        List<BookPageDto> bookCart = book2UserReadRepository.findByUser(user)
                .stream()
                .filter(book2User -> book2User.getBook2UserType().equals(cartType))
                .map(Book2User::getBook)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

        int sum = bookCart.stream()
                .map(BookPageDto::getPrice)
                .mapToInt(Integer::intValue)
                .sum();
        int sumDiscount = bookCart.stream()
                .map(BookPageDto::getDiscountPrice)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("sum", sum);
        model.addAttribute("sumDiscount", sumDiscount);
        model.addAttribute("bookCart", bookCart);

        return "cart";
    }
}
