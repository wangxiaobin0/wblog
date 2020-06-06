package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ColumnItemDao;
import com.wblog.model.entity.ColumnItemEntity;
import com.wblog.service.ColumnItemService;


@Service("columnItemService")
public class ColumnItemServiceImpl extends ServiceImpl<ColumnItemDao, ColumnItemEntity> implements ColumnItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ColumnItemEntity> page = this.page(
                new Query<ColumnItemEntity>().getPage(params),
                new QueryWrapper<ColumnItemEntity>()
        );

        return new PageUtils(page);
    }

}