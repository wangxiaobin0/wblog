package com.wblog.controller.article;

import java.util.Arrays;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.vo.ArticlePostVo;
import com.wblog.model.vo.ArticleShowVo;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.BeanUtils;
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

    /**
     * 列表
     */
    @SysLog(business = "查询所有文章")
    @GetMapping("/list")
    public String listAll(@RequestParam Map<String, Object> params, Model model){
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
    @GetMapping("/list/draft")
    public String list(@RequestParam Map<String, Object> params, Model model) {

        PageUtils page = articleService.listDraft(params);
        model.addAttribute("page", page);
        return "admin/article/draft";
    }

    /**
     * 已删除列表
     * @param params
     * @return
     */
    @SysLog(business = "查询回收站列表")
    @GetMapping("/list/trash")
    public String listTrash(@RequestParam Map<String, Object> params, Model model) {

        PageUtils page = articleService.listTrash(params);
        model.addAttribute("page", page);
        return "admin/article/draft";
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
        ArticleShowVo showVo = new ArticleShowVo();
        BeanUtils.copyProperties(articlePostVo, showVo);
        model.addAttribute("item", showVo);
        return "item";
    }

    /**
     * 保存
     */
    @SysLog(business = "发表博客")
    @PostMapping
    public String save(ArticlePostVo article){
		articleService.save(article);
        return "redirect:/admin/article/list";
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
