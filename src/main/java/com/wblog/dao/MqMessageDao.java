package com.wblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wblog.model.entity.MqMessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessageEntity> {
	
}
