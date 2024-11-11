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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class CartPageController {

    private final Book2UserTypeRepository book2UserTypeRepository;
    private final UserRegisterService userRegisterService;
    private final Book2UserRepository book2UserRepository;
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
        Book2UserType cartType = book2UserTypeRepository.findByCode("CART").orElse(null);

        List<BookPageDto> bookCart = book2UserRepository.findByUser(user)
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
