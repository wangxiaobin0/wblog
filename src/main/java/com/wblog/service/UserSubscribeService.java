package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.UserSubscribeEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface UserSubscribeService extends IService<UserSubscribeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

