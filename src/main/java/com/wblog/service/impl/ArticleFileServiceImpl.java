package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleFileDao;
import com.wblog.service.ArticleFileService;
import com.wblog.model.entity.ArticleFileEntity;

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

}