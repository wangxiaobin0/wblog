package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ColumnTagEntity;

import java.util.Map;

/**
 * 专栏/标签关联表
 *
 * @author wangxb
 * @email 
 */
public interface ColumnTagService extends IService<ColumnTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

