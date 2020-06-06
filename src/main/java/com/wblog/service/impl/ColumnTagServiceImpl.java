package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ColumnTagDao;
import com.wblog.model.entity.ColumnTagEntity;
import com.wblog.service.ColumnTagService;


@Service("columnTagService")
public class ColumnTagServiceImpl extends ServiceImpl<ColumnTagDao, ColumnTagEntity> implements ColumnTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ColumnTagEntity> page = this.page(
                new Query<ColumnTagEntity>().getPage(params),
                new QueryWrapper<ColumnTagEntity>()
        );

        return new PageUtils(page);
    }

}