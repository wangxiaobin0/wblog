package com.wblog.dao;

import com.wblog.model.entity.ArticleTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wblog.model.vo.TagStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTagEntity> {

    List<TagStatisticsVo> queryTagStatistics(@Param("keys") Set<String> keys);
}
