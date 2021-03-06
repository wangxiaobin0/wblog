package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.BloggerTo;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface AdminProfileService extends IService<AdminProfileEntity> {
    AdminProfileEntity getProfileBySession();

    AdminTo updateProfile(AdminProfileEntity profileEntity);

    BloggerTo getBloggerInfo();

    AdminTo updateImg(String key, String url);
}

