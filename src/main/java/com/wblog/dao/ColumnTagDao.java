package com.wblog.dao;

import com.wblog.model.entity.ColumnTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 专栏/标签关联表
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface ColumnTagDao extends BaseMapper<ColumnTagEntity> {
	
}
