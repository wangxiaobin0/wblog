package com.wblog.service.impl;

import com.wblog.common.constant.ArticleConstant;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.exception.ArticleException;
import com.wblog.exception.ColumnException;
import com.wblog.model.entity.*;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.service.ArticleRedisService;
import com.wblog.service.ArticleService;
import com.wblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ColumnItemDao;
import com.wblog.service.ColumnItemService;


@Service("columnItemService")
public class ColumnItemServiceImpl extends ServiceImpl<ColumnItemDao, ColumnItemEntity> implements ColumnItemService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    TagService tagService;

    @Autowired
    ArticleRedisService articleRedisService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ColumnItemEntity> page = this.page(
                new Query<ColumnItemEntity>().getPage(params),
                new QueryWrapper<ColumnItemEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ColumnItemVo> getColumnItems(Long id) {
        List<ColumnItemVo> itemVos = this.baseMapper.getColumnItems(id);
        return itemVos.stream().map(item -> {
            Long count = articleRedisService.getCount(item.getArticleId(), ArticleConstant.ARTICLE_COLLECT);
            item.setCollectNum(count);
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public void addToColumn(ColumnItemEntity columnItem) {
        ArticleEntity articleEntity = articleService.getById(columnItem.getArticleId());
        if (articleEntity == null) {
            throw new ArticleException("文章不存在");
        }
        if (articleEntity.getState() != ArticleStateEnum.PUBLIC.getCode()) {
            throw new ColumnException("非公开文章不能添加在专栏中");
        }
        this.save(columnItem);
    }

    @Override
    public Boolean changeSort(Long id, Boolean sort) {
        Integer integer = this.baseMapper.changeSort(id, sort);
        return integer == 1;
    }
}