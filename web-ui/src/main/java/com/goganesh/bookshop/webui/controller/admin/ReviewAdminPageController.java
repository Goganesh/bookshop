package com.goganesh.bookshop.webui.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reviews")
public class ReviewAdminPageController {

    @GetMapping()
    public String getReviewsPage() {
        return "admin/reviews";
    }

    @GetMapping("/{id}")
    public String getReviewPage(@PathVariable Integer id) {
        return "admin/review";
    }


}
