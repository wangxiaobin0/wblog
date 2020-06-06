package com.wblog.dao;

import com.wblog.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
