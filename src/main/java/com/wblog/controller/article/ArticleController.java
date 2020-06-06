package com.wblog.controller.article;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ArticleEntity;
import com.wblog.service.ArticleService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
		ArticleEntity article = articleService.getById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ArticleEntity article){
		articleService.save(article);

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
