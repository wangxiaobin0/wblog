package com.wblog.service;

import com.wblog.model.vo.ArticleIndexVo;

import java.util.List;
import java.util.Set;

/**
 * 文章的redis服务
 */
public interface ArticleRedisService {
    /**
     * 更新文章浏览数
     * @param articleId
     * @return
     */
    Long incrViewCountAndAddViewHistory(Long articleId);

    /**
     * 获取文章数量信息
     * @param articleId id
     * @param keyPrefix key前缀，参见ArticleConstant
     * @return
     */
    Long getCount(Long articleId, String keyPrefix);

    /**
     * 获取文章的点赞/收藏者列表
     * @param articleId 文章id
     * @param keyPrefix key前缀，参见ArticleConstant
     * @return
     */
    Set<String> getList(Long articleId, String keyPrefix);

    /**
     * 用户是否赞了/收藏了
     * @param articleId
     * @param keyPrefix
     * @return
     */
    Boolean orNot(Long articleId, String keyPrefix, String userKey);

    /**
     * 点赞/取消点赞
     * @param articleId 文章id
     * @param flag true：点赞；false：取消
     */
    Boolean collectOrCancel(Long articleId, Boolean flag);

    /**
     * 收藏/取消收藏
     * @param articleId 文章id
     * @param flag true：收藏；false：取消
     * @return
     */
    Boolean thumbUpOrCancel(Long articleId, Boolean flag);

    /**
     * 获取用户浏览/点赞/收藏/订阅ids
     * @param keyPrefix
     * @return
     */
    Set<String> getUserView(String keyPrefix);
}
