package com.goganesh.bookshop.webui.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/admin/books")
public class BookAdminPageController {

    @GetMapping()
    public String getBooksPage() {
        return "admin/books";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable Integer id) {
        return "admin/book";
    }

    @GetMapping("/{id}/image")
    public String getBookImagePage(@PathVariable Integer id) {
        return "admin/book_image";
    }

}
