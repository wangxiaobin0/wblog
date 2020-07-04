package com.wblog.service.impl;

import com.wblog.common.constant.ArticleConstant;
import com.wblog.common.constant.UserConstant;
import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.service.ArticleRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ArticleRedisServiceImpl implements ArticleRedisService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Long incrViewCountAndAddViewHistory(Long articleId) {
        String userKey = ThreadLocalUtils.getUserTo().getUserKey();
        //添加访客的浏览记录
        redisTemplate.opsForSet().add(UserConstant.USER_VIEW_ARTICLE + userKey, articleId.toString());
        //添加文章的访客记录
        redisTemplate.opsForSet().add(ArticleConstant.ARTICLE_VIEW + articleId, userKey);
        return redisTemplate.opsForValue().increment(ArticleConstant.ARTICLE_VIEW_COUNT + articleId);
    }

    @Override
    public Long getCount(Long articleId, String keyPrefix) {
        String key = keyPrefix + articleId;
        //浏览数是累计数，不是统计不重复的浏览访客数
        if (ArticleConstant.ARTICLE_VIEW_COUNT.equals(keyPrefix)) {
            String s = redisTemplate.opsForValue().get(key);
            return s == null ? 0l : Long.parseLong(s);
        } else {
            BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(key);
            return setOps.size();
        }
    }

    @Override
    public Set<String> getList(Long articleId, String keyPrefix) {
        String key = keyPrefix + articleId;
        Set<String> members = redisTemplate.opsForSet().members(key);
        return members;
    }

    @Override
    public Boolean orNot(Long articleId, String keyPrefix, String userKey) {
        String key = keyPrefix + articleId;
        return redisTemplate.opsForSet().isMember(key, userKey);
    }

    @Override
    public Boolean collectOrCancel(Long articleId, Boolean flag) {
        //用户key
        String userKey = ThreadLocalUtils.getUserTo().getUserKey();

        //文章redisKey
        String articleRedisKey = ArticleConstant.ARTICLE_COLLECT + articleId;
        BoundSetOperations<String, String> articleSetOps = setOps(articleRedisKey);
        //用户redisKey
        String userRedisKey = UserConstant.USER_COLLECT_ARTICLE + userKey;
        BoundSetOperations<String, String> userSetOps = setOps(userRedisKey);
        //flag=true:点赞；flag=false:取消点赞
        if (flag) {
            //添加该访客到文章点赞列表
            articleSetOps.add(userKey);
            //添加文章到该访客的点赞列表
            userSetOps.add(articleId.toString());
        } else {
            //文章点赞列表中移除该访客
            articleSetOps.remove(userKey);
            //访客的点赞列表移除该文章
            userSetOps.remove(articleId.toString());
        }
        return true;
    }

    @Override
    public Boolean thumbUpOrCancel(Long articleId, Boolean flag) {
        //用户key
        String userKey = ThreadLocalUtils.getUserTo().getUserKey();

        //文章redisKey
        String articleRedisKey = ArticleConstant.ARTICLE_THUMB_UP + articleId;
        BoundSetOperations<String, String> articleSetOps = setOps(articleRedisKey);
        //用户redisKey
        String userRedisKey = UserConstant.USER_THUMB_UP_ARTICLE + userKey;
        BoundSetOperations<String, String> userSetOps = setOps(userRedisKey);
        //flag=true:添加收藏；flag=false:取消收藏
        if (flag) {
            //没有点赞
            if (!articleSetOps.isMember(userKey)) {
                //添加该访客到文章收藏列表
                articleSetOps.add(userKey);
                //添加文章到该访客的收藏列表
                return userSetOps.add(articleId.toString()) == 1;
            }
        } else {
            //已收藏
            if (articleSetOps.isMember(userKey)) {
                //文章收藏列表中移除该访客
                articleSetOps.remove(userKey);
                //访客的收藏列表移除该文章
                return userSetOps.remove(articleId.toString()) == 1;
            }
        }
        return true;
    }

    @Override
    public Set<String> getUserView(String keyPrefix) {
        String userKey = ThreadLocalUtils.getUserTo().getUserKey();
        BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(keyPrefix + userKey);
        Set<String> ids = setOps.members();
        return ids;
    }

    private BoundSetOperations<String, String> setOps(String key) {
        return redisTemplate.boundSetOps(key);
    }
}
