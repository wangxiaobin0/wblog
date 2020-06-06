package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.UserLogDao;
import com.wblog.model.entity.UserLogEntity;
import com.wblog.service.UserLogService;


@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserLogEntity> page = this.page(
                new Query<UserLogEntity>().getPage(params),
                new QueryWrapper<UserLogEntity>()
        );

        return new PageUtils(page);
    }

}