package com.wblog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wblog.common.constant.ArticleConstant;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.exception.ArticleException;
import com.wblog.exception.ColumnException;
import com.wblog.model.entity.*;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.service.ArticleRedisService;
import com.wblog.service.ArticleService;
import com.wblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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


@Slf4j
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
    public PageResult queryColumnItemByPage(Long id, Long page, Long size){
        log.info("分页查询专栏内文章流程开始。id：{}，page：{}，size：{}", id, page, size);
        PageHelper.startPage(page.intValue(), size.intValue());
        List<ColumnItemVo> itemVos = this.baseMapper.getColumnItems(id);
        log.info("查询专栏内文章");
        for (ColumnItemVo vo : itemVos) {
            Long count = articleRedisService.getCount(vo.getArticleId(), ArticleConstant.ARTICLE_COLLECT);
            vo.setCollectNum(count);
        }
        log.info("查询文章收藏人数");
        PageInfo<ColumnItemVo> pageInfo = new PageInfo<>(itemVos);
        return new PageResult(pageInfo);
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

    @Override
    public List<ColumnItemVo> getUnAddArticle(Long columnId) {
        return this.baseMapper.getUnAddArticle(columnId);
    }
}