package com.goganesh.bookshop.webui.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {

    @GetMapping("/admin")
    public String getMainPage() {
        return "admin/index";
    }
}
