package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.UserMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface UserMessageService extends IService<UserMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

