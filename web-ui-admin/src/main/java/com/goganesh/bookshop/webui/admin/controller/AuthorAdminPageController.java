package com.goganesh.bookshop.webui.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/admin/authors")
public class AuthorAdminPageController {

    @GetMapping()
    public String getAuthorsPage() {
        return "admin/authors";
    }

    @GetMapping("/{id}")
    public String getAuthorPage(@PathVariable Integer id) {
        return "admin/author";
    }
}
