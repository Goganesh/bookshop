package com.goganesh.security.controller;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.UserWriteRepository;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Builder
public class ProfileController {

    private final UserWriteRepository userWriteRepository;
    private final UserRegisterService userRegisterService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/change-profile")
    public RedirectView changeProfile(@RequestParam MultiValueMap<String,String> paramMap,
                                      RedirectAttributes attributes) {
        User user = userRegisterService.getCurrentUser();
        String newName = paramMap.get("name").get(0);

        user.setName(newName);
        userWriteRepository.save(user);
        attributes.addFlashAttribute("profileChangeResult", "Профиль успешно сохранен");
        return new RedirectView("profile");
    }

}
