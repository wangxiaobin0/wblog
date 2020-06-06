package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.UserSubscribeDao;
import com.wblog.model.entity.UserSubscribeEntity;
import com.wblog.service.UserSubscribeService;


@Service("userSubscribeService")
public class UserSubscribeServiceImpl extends ServiceImpl<UserSubscribeDao, UserSubscribeEntity> implements UserSubscribeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserSubscribeEntity> page = this.page(
                new Query<UserSubscribeEntity>().getPage(params),
                new QueryWrapper<UserSubscribeEntity>()
        );

        return new PageUtils(page);
    }

}