package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.SystemLogEntity;
import com.wblog.model.vo.SystemLogVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface SystemLogService extends IService<SystemLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SystemLogVo> getLog();
}

