package com.goganesh.bookshop.webui.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/admin/genres")
public class GenreAdminPageController {

    @GetMapping()
    public String getGenresPage() {
        return "admin/genres";
    }

    @GetMapping("/{id}")
    public String getGenrePage(@PathVariable Integer id) {
        return "admin/genre";
    }
}
