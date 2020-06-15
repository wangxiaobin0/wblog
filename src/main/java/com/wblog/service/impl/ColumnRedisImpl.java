package com.wblog.service.impl;

import com.wblog.common.constant.ColumnConstant;
import com.wblog.common.constant.UserConstant;
import com.wblog.controller.user.UserController;
import com.wblog.interceptor.UserRequestInterceptor;
import com.wblog.service.ColumnRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ColumnRedisImpl implements ColumnRedisService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Long getCount(Long columnId) {

        //取订阅专栏的用户个数为订阅数
        String columnSubscribeKey = ColumnConstant.COLUMN_SUBSCRIBE + columnId;
        BoundSetOperations<String, String> columnSetOps = redisTemplate.boundSetOps(columnSubscribeKey);
        Long count = columnSetOps.size();
        return count;
    }

    @Override
    public void subscribe(Long columnId, Boolean flag) {
        String userKey = UserRequestInterceptor.getUser().getUserKey();
        //专栏订阅用户key
        String columnSubscribeKey = ColumnConstant.COLUMN_SUBSCRIBE + columnId;
        BoundSetOperations<String, String> columnSetOps = redisTemplate.boundSetOps(columnSubscribeKey);

        //访客订阅专栏key
        String userSubscribeKey = UserConstant.USER_COLUMN_SUBSCRIBE + userKey;
        BoundSetOperations<String, String> userSetOps = redisTemplate.boundSetOps(userSubscribeKey);
        //true：添加到收藏；false：取消收藏
        //redis的写操作，天然幂等
        if (flag) {
            //添加访客到订阅专栏的列表中
            columnSetOps.add(userKey);

            //添加专栏到访客订阅列表
            userSetOps.add(columnId.toString());
        } else {
            //把访客从订阅专栏的列表中移除
            columnSetOps.remove(userKey);

            //把专栏从访客订阅列表中移除
            userSetOps.remove(columnId.toString());
        }
    }

    @Override
    public Boolean hasSubscribe(Long columnId, String userKey) {
        String columnSubscribeKey = ColumnConstant.COLUMN_SUBSCRIBE + columnId;
        BoundSetOperations<String, String> columnSetOps = redisTemplate.boundSetOps(columnSubscribeKey);
        return columnSetOps.isMember(userKey);
    }
}
