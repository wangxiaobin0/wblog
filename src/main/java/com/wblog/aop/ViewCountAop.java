package com.wblog.aop;

import com.wblog.common.constant.BlogConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 全局更新访问次数AOP
 */
@Slf4j
@Aspect
@Component
public class ViewCountAop {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 切入点为@ViewCount注解
     */
    @Pointcut("@annotation(com.wblog.annotation.ViewCount)")
    public void pointCut() {
    }

    /**
     * 前置通知
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinpoint) {
        redisTemplate.opsForValue().increment(BlogConstant.BLOG_VIEW_COUNT);
    }
}
