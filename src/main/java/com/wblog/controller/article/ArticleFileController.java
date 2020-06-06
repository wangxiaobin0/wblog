package com.wblog.controller.article;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.entity.ArticleFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.service.ArticleFileService;


/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/artilefile")
public class ArticleFileController {
    @Autowired
    private ArticleFileService artileFileService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:artilefile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = artileFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ArticleFileEntity artileFile = artileFileService.getById(id);

        return R.ok().put("artileFile", artileFile);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ArticleFileEntity artileFile){
		artileFileService.save(artileFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ArticleFileEntity artileFile){
		artileFileService.updateById(artileFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		artileFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
