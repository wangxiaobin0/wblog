package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.UserLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface UserLogService extends IService<UserLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

