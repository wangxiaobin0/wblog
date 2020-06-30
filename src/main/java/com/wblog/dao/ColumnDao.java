package com.wblog.dao;

import com.wblog.model.entity.ColumnEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wblog.model.vo.ColumnVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface ColumnDao extends BaseMapper<ColumnEntity> {

    List<ColumnVo> columnList();

    List<ColumnVo> unAddColumn(@Param("articleId") Long articleId);
}
