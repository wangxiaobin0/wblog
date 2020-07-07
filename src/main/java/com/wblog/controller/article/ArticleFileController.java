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
}