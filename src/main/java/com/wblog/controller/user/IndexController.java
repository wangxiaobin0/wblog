package com.wblog.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "index.html"})
    public String index() {
        return "index";
    }


    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/item")
    public String item() {
        return "item";
    }
    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    @GetMapping("/column")
    public String column() {
        return "column";
    }
}
