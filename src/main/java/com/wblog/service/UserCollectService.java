package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.UserCollectEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface UserCollectService extends IService<UserCollectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

