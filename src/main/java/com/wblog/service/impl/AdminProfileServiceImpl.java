package com.wblog.service.impl;

import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.BloggerTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.AdminProfileDao;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.service.AdminProfileService;

@Slf4j
@Service("adminProfileService")
public class AdminProfileServiceImpl extends ServiceImpl<AdminProfileDao, AdminProfileEntity> implements AdminProfileService {

    @Override
    public AdminProfileEntity getProfileBySession() {
        AdminTo admin = ThreadLocalUtils.getAdminTo();
        Long adminId = admin.getAdminId();
        QueryWrapper<AdminProfileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", adminId);
        AdminProfileEntity profileEntity = this.getOne(queryWrapper);
        return profileEntity;
    }

    @Override
    public AdminTo updateProfile(AdminProfileEntity profileEntity) {
        AdminTo session = ThreadLocalUtils.getAdminTo();
        profileEntity.setId(session.getId());
        if (!profileEntity.getSocialGithub().startsWith("https://")) {
            profileEntity.setSocialGithub("https://" + profileEntity.getSocialGithub());
        }
        if (!profileEntity.getSocialWeibo().startsWith("https://")) {
            profileEntity.setSocialWeibo("https://" + profileEntity.getSocialWeibo());
        }

        this.updateById(profileEntity);

        //查询最新的个人资料，用于存入session
        AdminProfileEntity byId = this.getById(session.getId());
        AdminTo adminTo = new AdminTo();
        BeanUtils.copyProperties(byId, adminTo);
        log.info("最新个人资料为：{}", adminTo);
        return adminTo;
    }

    @Override
    public BloggerTo getBloggerInfo() {
        AdminProfileEntity profileEntity = this.getOne(null);
        BloggerTo bloggerTo = new BloggerTo();
        BeanUtils.copyProperties(profileEntity, bloggerTo);
        return bloggerTo;
    }
}