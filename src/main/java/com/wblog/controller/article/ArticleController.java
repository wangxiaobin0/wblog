package com.wblog.controller.article;

import com.wblog.annotation.SysLog;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.R;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.model.vo.ArticlePostVo;
import com.wblog.model.vo.ArticlePreviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ArticleEntity;
import com.wblog.service.ArticleService;



/**
 * 
 * @author wangxb
 * @email 
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 管理端：跳转添加文章页面
     * @return
     */
    @GetMapping
    public String add() {
        return "admin/article/add";
    }

    /**
     * 管理端：已发布文章列表
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/publish")
    public String listPublish(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Long size,
                              Model model){
        PageResult pageResult = articleService.listPublish(page, size);
        model.addAttribute("page", pageResult);
        return "admin/article/list";
    }

    /**
     * 管理端：草稿箱列表
     * @return
     */
    @GetMapping("/draft")
    public String listDraft(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") Long size,
                            Model model) {
        PageResult pageResult = articleService.listDraftOrTrash(page, size, ArticleStateEnum.DRAFT);
        model.addAttribute("page", pageResult);
        return "admin/article/draft";
    }

    /**
     * 管理端：回收站
     * @return
     */
    @GetMapping("/trash")
    public String listTrash(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") Long size,
                            Model model) {
        PageResult pageResult = articleService.listDraftOrTrash(page, size, ArticleStateEnum.TRASH);
        model.addAttribute("page", pageResult);
        return "admin/article/trash";
    }

    /**
     * 管理端：查看文章详情
     */
    @GetMapping("/{id}")
    public String info(@PathVariable("id") Long id, Model model){
        ArticleItemVo item = articleService.getDetail(id);
        model.addAttribute("article", item);
        return "admin/fragment/article::article";
    }

    /**
     * 管理端：预览
     * @param articlePostVo
     * @param model
     * @return
     */
    @PostMapping("/preview")
    public String preview(ArticlePostVo articlePostVo, Model model){
        ArticlePreviewVo showVo = articleService.preview(articlePostVo);
        model.addAttribute("article", showVo);
        return "admin/article/preview";
    }

    /**
     * 管理端：发表文章
     */
    @SysLog(business = "发表博客")
    @PostMapping
    public String save(ArticlePostVo article){
		articleService.save(article);
        return "redirect:/admin/article/publish";
    }

    /**
     * 管理端：修改置顶状态
     * @param id
     * @param top
     * @return
     */
    @SysLog(business = "修改博客置顶状态")
    @PostMapping("/top")
    @ResponseBody
    public R updateTop(@RequestParam("articleId") Long id,
                            @RequestParam("top") Boolean top){
        articleService.updateTop(id, top);
        return R.ok();
    }

    /**
     * 管理端：修改博客状态
     * @param id
     * @param state
     * @return
     */
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
     * 管理端：删除文章
     */
    @SysLog(business = "删除文章")
    @DeleteMapping
    public String delete(@RequestParam("id") Long id){
		articleService.delete(id);
        return "redirect:/admin/article/trash";
    }
}
