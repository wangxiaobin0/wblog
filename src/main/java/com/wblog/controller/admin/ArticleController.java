package com.wblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminArticle")
@RequestMapping("/admin/article")
public class ArticleController {

    @GetMapping("/lists")
    public String lists() {
        return "admin/article/list";
    }

    @GetMapping
    public String list() {
        return "admin/article/add";
    }
}
