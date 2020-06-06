package com.wblog.controller.article;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.service.ArticleTagService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/articletag")
public class ArticleTagController {
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:articletag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleTagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:articletag:info")
    public R info(@PathVariable("id") Long id){
		ArticleTagEntity articleTag = articleTagService.getById(id);

        return R.ok().put("articleTag", articleTag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:articletag:save")
    public R save(@RequestBody ArticleTagEntity articleTag){
		articleTagService.save(articleTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:articletag:update")
    public R update(@RequestBody ArticleTagEntity articleTag){
		articleTagService.updateById(articleTag);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:articletag:delete")
    public R delete(@RequestBody Long[] ids){
		articleTagService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
