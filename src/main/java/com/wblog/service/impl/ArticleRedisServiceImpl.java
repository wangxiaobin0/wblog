package com.wblog.service.impl;

import com.wblog.common.constant.ArticleConstant;
import com.wblog.service.ArticleRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleRedisServiceImpl implements ArticleRedisService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Long incrViewCount(Long articleId) {
        return redisTemplate.opsForValue().increment(ArticleConstant.ARTICLE_VIEW_COUNT + articleId);
    }

    @Override
    public String getCollectCount(Long articleId, String keyPrefix) {
        return redisTemplate.opsForValue().get(keyPrefix + articleId);
    }
}
