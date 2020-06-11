package com.wblog.controller.user;

import com.wblog.annotation.ArticleViewCount;
import com.wblog.annotation.ViewCount;
import com.wblog.model.vo.ArticleIndexVo;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.model.vo.TagVo;
import com.wblog.service.ArticleService;
import com.wblog.service.ArticleTagService;
import com.wblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @ViewCount
    @GetMapping(value = {"/", "/index", "index.html"})
    public String index(Model model) {
        List<ArticleIndexVo> articleIndexVos = articleService.indexList();
        model.addAttribute("indexList", articleIndexVos);
        return "index";
    }


    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/item/{articleId}")
    public String item(@PathVariable("articleId") Long articleId, Model model) {
        ArticleItemVo articleItem = articleService.getItem(articleId);
        model.addAttribute("article", articleItem);
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
