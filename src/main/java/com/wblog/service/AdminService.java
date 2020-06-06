package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.AdminEntity;
import com.wblog.model.vo.RegisterVo;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface AdminService extends IService<AdminEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(RegisterVo registerVo);
}

