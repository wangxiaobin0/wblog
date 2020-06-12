package com.wblog.controller.article;

import java.util.Arrays;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.vo.ArticlePostVo;
import com.wblog.model.vo.ArticlePreviewVo;
import com.wblog.service.ArticleRedisService;
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
    @SysLog(business = "查询已发布文章")
    @GetMapping("/publish")
    public String listPublish(@RequestParam Map<String, Object> params, Model model){
        PageUtils page = articleService.queryPage(params);
        model.addAttribute("page", page);
        return "admin/article/list";
    }

    /**
     * 草稿箱列表
     * @param params
     * @return
     */
    @SysLog(business = "查询草稿箱列表")
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
    @SysLog(business = "查询回收站列表")
    @GetMapping("/trash")
    public String listTrash(@RequestParam Map<String, Object> params, Model model) {

        PageUtils page = articleService.listTrash(params);
        model.addAttribute("page", page);
        return "admin/article/trash";
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
		ArticleEntity article = articleService.getById(id);

        return R.ok().put("article", article);
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
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		articleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
