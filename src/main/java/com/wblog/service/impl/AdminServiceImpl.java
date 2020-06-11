package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.exception.AuthException;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.BloggerTo;
import com.wblog.model.vo.LoginVo;
import com.wblog.model.vo.RegisterVo;
import com.wblog.service.AdminProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.AdminDao;
import com.wblog.model.entity.AdminEntity;
import com.wblog.service.AdminService;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {

    @Autowired
    AdminProfileService adminProfileService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminEntity> page = this.page(
                new Query<AdminEntity>().getPage(params),
                new QueryWrapper<AdminEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void register(RegisterVo registerVo) {
        AdminEntity adminEntity = new AdminEntity();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(registerVo.getPassword());
        adminEntity.setPassword(encodePassword);
        this.save(adminEntity);
        log.info("保存注册信息{}", adminEntity);

        AdminProfileEntity profileEntity = new AdminProfileEntity();
        profileEntity.setAdminId(adminEntity.getId());
        profileEntity.setBlogName(registerVo.getBlogName());
        adminProfileService.save(profileEntity);
        log.info("保存个人资料{}", profileEntity);
    }

    @Override
    public AdminTo login(LoginVo loginVo) {
        AdminEntity byId = this.getById(loginVo.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(loginVo.getPassword(), byId.getPassword());
        if (!matches) {

            throw new AuthException("用户名或密码错误");
        }
        AdminProfileEntity adminProfile = adminProfileService.getOne(new QueryWrapper<AdminProfileEntity>().eq("admin_id", loginVo.getId()));

        AdminTo adminTo = new AdminTo();
        BeanUtils.copyProperties(adminProfile, adminTo);
        adminProfileService.updateById(adminProfile);
        return adminTo;
    }
}