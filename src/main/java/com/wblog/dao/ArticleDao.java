package com.wblog.dao;

import com.wblog.model.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wblog.model.vo.ArticleIndexVo;
import com.wblog.model.vo.ArticleItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {

    /**
     * 查询首页列表
     * @return
     */
    List<ArticleIndexVo> getIndexList();

    /**
     * 查询文章
     * @param articleId
     * @return
     */
    ArticleItemVo getArticleItem(@PathParam("id") Long articleId);

    /**
     * 查询已发布文章列表
     * @return
     */
    List<ArticleIndexVo> getPublishList();

    /**
     * 删除过期文章
     * @param id 文章ID
     * @param state 文章状态。3：草稿箱；4：回收站
     * @return
     */
    Integer deleteExpireArticle(@Param("id") Long id, @Param("state") Integer state);
}
