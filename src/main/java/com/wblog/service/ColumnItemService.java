package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ColumnItemEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ColumnItemService extends IService<ColumnItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

