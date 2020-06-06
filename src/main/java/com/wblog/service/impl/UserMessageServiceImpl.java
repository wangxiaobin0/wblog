package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.UserMessageDao;
import com.wblog.model.entity.UserMessageEntity;
import com.wblog.service.UserMessageService;


@Service("userMessageService")
public class UserMessageServiceImpl extends ServiceImpl<UserMessageDao, UserMessageEntity> implements UserMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserMessageEntity> page = this.page(
                new Query<UserMessageEntity>().getPage(params),
                new QueryWrapper<UserMessageEntity>()
        );

        return new PageUtils(page);
    }

}