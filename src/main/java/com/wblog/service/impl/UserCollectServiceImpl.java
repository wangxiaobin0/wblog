package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.UserCollectDao;
import com.wblog.model.entity.UserCollectEntity;
import com.wblog.service.UserCollectService;


@Service("userCollectService")
public class UserCollectServiceImpl extends ServiceImpl<UserCollectDao, UserCollectEntity> implements UserCollectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectEntity> page = this.page(
                new Query<UserCollectEntity>().getPage(params),
                new QueryWrapper<UserCollectEntity>()
        );

        return new PageUtils(page);
    }

}