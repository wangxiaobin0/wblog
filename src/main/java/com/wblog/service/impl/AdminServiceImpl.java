package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.model.vo.RegisterVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.AdminDao;
import com.wblog.model.entity.AdminEntity;
import com.wblog.service.AdminService;

import javax.rmi.PortableRemoteObject;


@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminEntity> page = this.page(
                new Query<AdminEntity>().getPage(params),
                new QueryWrapper<AdminEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(RegisterVo registerVo) {
        AdminProfileEntity profileEntity = new AdminProfileEntity();
        profileEntity.setBlogName(registerVo.getBlogName());

        AdminEntity adminEntity = new AdminEntity();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(adminEntity.getPassword());
        adminEntity.setPassword(encodePassword);
    }

}