package com.wblog.dao;

import com.wblog.model.entity.ColumnItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wblog.model.vo.ColumnItemVo;
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
public interface ColumnItemDao extends BaseMapper<ColumnItemEntity> {
    List<ColumnItemVo> getColumnItems(@Param("columnId") Long id);

    Integer changeSort(@Param("id") Long id, @Param("sort") Boolean sort);

    List<ColumnItemVo> getUnAddArticle(@Param("id") Long columnId);
}
