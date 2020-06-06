package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.AdminProfileDao;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.service.AdminProfileService;


@Service("adminProfileService")
public class AdminProfileServiceImpl extends ServiceImpl<AdminProfileDao, AdminProfileEntity> implements AdminProfileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminProfileEntity> page = this.page(
                new Query<AdminProfileEntity>().getPage(params),
                new QueryWrapper<AdminProfileEntity>()
        );

        return new PageUtils(page);
    }

}