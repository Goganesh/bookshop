package com.goganesh.bookshop.webui.controller.client;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import com.goganesh.bookshop.webui.client.dto.BalanceTransactionDto;
import com.goganesh.bookshop.webui.client.dto.SearchWordDto;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import com.goganesh.bookshop.webui.client.mapper.BalanceTransactionUiMapper;
import com.goganesh.bookshop.webui.client.mapper.UserMapper;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Builder
public class ProfilePageController {

    private final UserRegisterService userRegisterService;
    private final BalanceTransactionRestService balanceTransactionRestService;
    private final BalanceTransactionUiMapper balanceTransactionUiMapper;
    private final UserMapper userMapper;

    @ModelAttribute("profileChangeResult")
    public String profileSuccess() {
        return "";
    }

    @ModelAttribute("currentUser")
    public UserPageDto user() {
        User user = userRegisterService.getCurrentUser();
        return userMapper.toDto(user);
    }

    @ModelAttribute("balanceTransactionDtos")
    public List<BalanceTransactionDto> balanceTransactionDtos() {
        User user = userRegisterService.getCurrentUser();
        int offset = 0;
        int limit = 50;
        Sort defaultSortOrder = Sort.by("time").descending();

        return balanceTransactionRestService.getPageByUser(user, offset, limit, defaultSortOrder)
                .stream()
                .map(balanceTransactionUiMapper::toDto)
                .collect(Collectors.toList());
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @GetMapping("/profile")
    public String handleProfile() {
        return "profile";
    }
}
