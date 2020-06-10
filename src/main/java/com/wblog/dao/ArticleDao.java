package com.wblog.dao;

import com.wblog.model.entity.ArticleEntity;
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
public interface ArticleDao extends BaseMapper<ArticleEntity> {

    List<ArticleEntity> queryArticleByColumnId(@Param("columnId") Long id);
}
