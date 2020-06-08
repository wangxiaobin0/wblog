package com.wblog.dao;

import com.wblog.model.entity.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TagDao extends BaseMapper<TagEntity> {
    List<TagEntity> listTagByArticleId(@Param("articleId") Long articleId);
}
