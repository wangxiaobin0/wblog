package com.wblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wblog.common.utils.Query;
import com.wblog.model.entity.MqFailMessageEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.wblog.common.utils.PageUtils;

import com.wblog.dao.MqFailMessageDao;
import com.wblog.service.MqFailMessageService;


@Service("mqFailMessageService")
public class MqFailMessageServiceImpl extends ServiceImpl<MqFailMessageDao, MqFailMessageEntity> implements MqFailMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MqFailMessageEntity> page = this.page(
                new Query<MqFailMessageEntity>().getPage(params),
                new QueryWrapper<MqFailMessageEntity>()
        );

        return new PageUtils(page);
    }

}