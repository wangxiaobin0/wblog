package com.wblog.service.impl;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.UserDao;
import com.wblog.model.entity.UserEntity;
import com.wblog.service.UserService;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    SystemLogService systemLogService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @SysLog(business = "新增访客")
    @Override
    @Transactional
    public void addNewUser(String userKey) {
        log.info("新增访客开始。userKey:{}", userKey);
        //保存访客信息到数据库
        UserEntity userEntity = new UserEntity();
        userEntity.setUserKey(userKey);
        this.save(userEntity);
        log.info("保存访客信息成功。userKey{}:", userKey);
        log.info("新增访客流程结束。{}", userEntity);
    }

}