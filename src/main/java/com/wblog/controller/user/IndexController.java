package com.wblog.controller.user;

import com.wblog.annotation.ViewCount;
import com.wblog.common.constant.UserConstant;
import com.wblog.model.vo.*;
import com.wblog.service.ArticleService;
import com.wblog.service.ColumnItemService;
import com.wblog.service.ColumnService;
import com.wblog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@ViewCount
public class IndexController {

    @Autowired
    ArticleService articleService;

    @Autowired
    ColumnService columnService;

    @Autowired
    ColumnItemService columnItemService;

    @Autowired
    SearchService searchService;
    /**
     * 访客首页
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/index", "index.html"})
    public String index(Model model) {
        List<ArticleIndexVo> articleIndexVos = articleService.indexList();
        model.addAttribute("indexList", articleIndexVos);
        return "user/index";
    }


    /**
     * 搜索结果页面
     * @return
     */
    @GetMapping("/search")
    public String search(SearchParamVo searchParam, Model model) throws IOException {
        SearchResultVo result = searchService.search(searchParam);
        model.addAttribute("result", result);
        return "user/article/search";
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
        return "user/article/item";
    }

    /**
     * 归档
     * @return
     */
    @GetMapping("/archive")
    public String archive() {
        return "user/archive";
    }

    /**
     * 专栏
     * @return
     */
    @GetMapping("/column")
    public String column(Model model) {
        List<ColumnVo> columnList = columnService.columnList();
        List<ColumnVo> bannerList = columnList.stream().filter(ColumnVo::getBanner).collect(Collectors.toList());
        model.addAttribute("columnList", columnList);
        model.addAttribute("bannerList", bannerList);
        return "user/column/column";
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
        return "user/column/columnDetail";
    }

    @GetMapping("/view")
    public String View(Model model) throws ExecutionException, InterruptedException {
        UserViewVo views = articleService.getUserArticleList(UserConstant.USER_VIEW_ARTICLE);
        model.addAttribute("viewList", views);
        return "user/my/myView";
    }
    @GetMapping("/like")
    public String like(Model model) throws ExecutionException, InterruptedException {
        UserViewVo likes = articleService.getUserArticleList(UserConstant.USER_THUMB_UP_ARTICLE);
        model.addAttribute("likeList", likes);
        return "user/my/myLike";
    }
    @GetMapping("/collect")
    public String collect(Model model) throws ExecutionException, InterruptedException {
        UserViewVo collectList = articleService.getUserArticleList(UserConstant.USER_COLLECT_ARTICLE);
        model.addAttribute("collectList", collectList);
        return "user/my/myCollect";
    }
}
