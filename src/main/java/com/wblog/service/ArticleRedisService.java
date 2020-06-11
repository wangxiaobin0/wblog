package com.wblog.service;

/**
 * 文章的redis服务
 */
public interface ArticleRedisService {
    /**
     * 更新文章浏览数
     * @param articleId
     * @return
     */
    Long incrViewCount(Long articleId);

    /**
     * 获取文章数量信息
     * @param articleId id
     * @param keyPrefix key前缀，参见ArticleConstant
     * @return
     */
    String getCollectCount(Long articleId, String keyPrefix);
}
