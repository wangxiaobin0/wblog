package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.entity.*;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.service.ArticleService;
import com.wblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        return itemVos;
    }
}