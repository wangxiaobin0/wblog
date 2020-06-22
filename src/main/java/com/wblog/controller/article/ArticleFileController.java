package com.wblog.controller.article;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.entity.ArticleFileEntity;
import com.wblog.model.vo.ArticlePostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.service.ArticleFileService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("admin/article/file")
public class ArticleFileController {
    @Autowired
    private ArticleFileService articleFileService;


    @PostMapping
    public R loadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ArticlePostVo articlePostVo = articleFileService.loadFile(file);
        return R.ok().put("file", articlePostVo);
    }



    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ArticleFileEntity artileFile = articleFileService.getById(id);

        return R.ok().put("artileFile", artileFile);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ArticleFileEntity artileFile){
		articleFileService.save(artileFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ArticleFileEntity artileFile){
		articleFileService.updateById(artileFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		articleFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
