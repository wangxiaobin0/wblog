package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.MqFailMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface MqFailMessageService extends IService<MqFailMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

