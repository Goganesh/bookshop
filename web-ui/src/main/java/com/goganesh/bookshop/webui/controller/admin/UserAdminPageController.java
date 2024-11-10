package com.goganesh.bookshop.webui.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class UserAdminPageController {

    @GetMapping
    public String getUsersPage() {
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable Integer id) {
        return "admin/user";
    }
}
