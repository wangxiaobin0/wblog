package com.wblog.controller.article;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.vo.ArticleIndexVo;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.model.vo.ArticlePostVo;
import com.wblog.model.vo.ArticlePreviewVo;
import com.wblog.service.ArticleRedisService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ArticleEntity;
import com.wblog.service.ArticleService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    ArticleRedisService articleRedisService;

    /**
     * 列表
     */
    @GetMapping("/publish")
    public String listPublish(Model model){
        List<ArticleIndexVo> articleList = articleService.getPublishList();
        model.addAttribute("articleList", articleList);
        return "admin/article/list";
    }

    @GetMapping
    public String list() {
        return "admin/article/add";
    }
    /**
     * 草稿箱列表
     * @param params
     * @return
     */
    @GetMapping("/draft")
    public String listDraft(@RequestParam Map<String, Object> params, Model model) {
        PageUtils page = articleService.listDraft(params);
        model.addAttribute("page", page);
        return "admin/article/draft";
    }

    /**
     * 回收站
     * @param params
     * @return
     */
    @GetMapping("/trash")
    public String listTrash(@RequestParam Map<String, Object> params, Model model) {

        PageUtils page = articleService.listTrash(params);
        model.addAttribute("page", page);
        return "admin/article/trash";
    }

    /**
     * 管理端查看文章详情
     */
    @GetMapping("/{id}")
    public String info(@PathVariable("id") Long id, Model model){
        ArticleItemVo item = articleService.getDetail(id);
        model.addAttribute("article", item);
        return "/admin/fragment/article :: article";
    }

    @PostMapping("/preview")
    public String preview(ArticlePostVo articlePostVo, Model model){
        ArticlePreviewVo showVo = articleService.preview(articlePostVo);
        model.addAttribute("article", showVo);
        return "preview";
    }

    /**
     * 保存
     */
    @SysLog(business = "发表博客")
    @PostMapping
    public String save(ArticlePostVo article){
		articleService.save(article);
        return "redirect:/admin/article/publish";
    }

    @SysLog(business = "修改博客置顶状态")
    @PostMapping("/top")
    @ResponseBody
    public R updateTop(@RequestParam("articleId") Long id,
                            @RequestParam("top") Boolean top){
        articleService.updateTop(id, top);
        return R.ok();
    }

    @SysLog(business = "修改博客状态")
    @PostMapping("/state")
    @ResponseBody
    public R updateState(@RequestParam("articleId") Long id,
                       @RequestParam("state") Integer state){
        articleService.updateState(id, state);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ArticleEntity article){
		articleService.updateById(article);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(business = "删除文章")
    @DeleteMapping
    public String delete(@RequestParam("id") Long id){
		articleService.delete(id);
        return "redirect:/admin/article/trash";
    }
}
