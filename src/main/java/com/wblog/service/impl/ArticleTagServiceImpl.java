package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.vo.TagStatisticsVo;
import com.wblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleTagDao;
import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.service.ArticleTagService;


@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTagEntity> implements ArticleTagService {

    @Autowired
    TagService tagService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticleTagEntity> page = this.page(
                new Query<ArticleTagEntity>().getPage(params),
                new QueryWrapper<ArticleTagEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TagStatisticsVo> queryTagStatistics(Set<String> keys) {
        List<TagStatisticsVo> vos =  this.baseMapper.queryTagStatistics(keys);
        return vos;
    }

}