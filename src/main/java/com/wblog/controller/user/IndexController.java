package com.wblog.controller.user;

import com.wblog.annotation.ViewCount;
import com.wblog.model.vo.ArticleIndexVo;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnVo;
import com.wblog.service.ArticleService;
import com.wblog.service.ColumnItemService;
import com.wblog.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@ViewCount
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    ColumnService columnService;

    @Autowired
    ColumnItemService columnItemService;

    /**
     * 访客首页
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/index", "index.html"})
    public String index(Model model) {
        List<ArticleIndexVo> articleIndexVos = articleService.indexList();
        model.addAttribute("indexList", articleIndexVos);
        return "index";
    }


    /**
     * 搜索结果页面
     * @return
     */
    @GetMapping("/search")
    public String search() {
        return "search";
    }

    /**
     * 文章详情
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("/item/{articleId}")
    public String item(@PathVariable("articleId") Long articleId, Model model) {
        ArticleItemVo articleItem = articleService.getItem(articleId);
        model.addAttribute("article", articleItem);
        return "item";
    }

    /**
     * 归档
     * @return
     */
    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    /**
     * 专栏
     * @return
     */
    @GetMapping("/column")
    public String column(Model model) {
        List<ColumnVo> columnList = columnService.columnList();
        model.addAttribute("columnList", columnList);
        return "column";
    }

    /**
     * 专栏
     * @return
     */
    @GetMapping("/column/{columnId}")
    public String columnDetail(@PathVariable("columnId") Long columnId,
                         Model model) throws ExecutionException, InterruptedException {
        ColumnDetailVo columnDetail = columnService.columnDetail(columnId);
        model.addAttribute("column", columnDetail);
        return "columnDetail";
    }

    @GetMapping("/mine")
    public String mine(@CookieValue("userKey") String userKey) {
        return "";
    }
}
