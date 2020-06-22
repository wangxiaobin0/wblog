package com.wblog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.vo.ArticlePostVo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleFileDao;
import com.wblog.service.ArticleFileService;
import com.wblog.model.entity.ArticleFileEntity;
import org.springframework.web.multipart.MultipartFile;

@Service("artileFileService")
public class ArticleFileServiceImpl extends ServiceImpl<ArticleFileDao, ArticleFileEntity> implements ArticleFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticleFileEntity> page = this.page(
                new Query<ArticleFileEntity>().getPage(params),
                new QueryWrapper<ArticleFileEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public ArticlePostVo loadFile(MultipartFile file) throws IOException {
        //文件名
        String title = file.getOriginalFilename();
        //文件内容
        InputStream inputStream = file.getInputStream();
        String markdown = IoUtil.read(inputStream, Charset.defaultCharset());

        //封装返回
        ArticlePostVo postVo = new ArticlePostVo();
        postVo.setTitle(title.substring(0, title.lastIndexOf(".")));
        postVo.setMarkdown(markdown);
        return postVo;
    }

}