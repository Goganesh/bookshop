package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webui.dto.SearchWordDto;
import com.goganesh.bookshop.webui.dto.UserPageDto;
import com.goganesh.bookshop.webui.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@AllArgsConstructor
public class SigninPageController {

    private final UserRegisterService userRegisterService;
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

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }
}
