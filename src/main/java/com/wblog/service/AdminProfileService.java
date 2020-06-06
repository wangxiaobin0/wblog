package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.AdminProfileEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface AdminProfileService extends IService<AdminProfileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

