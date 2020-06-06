package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ColumnDao;
import com.wblog.model.entity.ColumnEntity;
import com.wblog.service.ColumnService;


@Service("columnService")
public class ColumnServiceImpl extends ServiceImpl<ColumnDao, ColumnEntity> implements ColumnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ColumnEntity> page = this.page(
                new Query<ColumnEntity>().getPage(params),
                new QueryWrapper<ColumnEntity>()
        );

        return new PageUtils(page);
    }

}