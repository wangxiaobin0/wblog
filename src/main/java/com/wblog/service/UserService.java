package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addNewUser(String userKey);
}

