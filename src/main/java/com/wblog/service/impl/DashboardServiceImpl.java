package com.wblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wblog.common.constant.BlogConstant;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.entity.ColumnEntity;
import com.wblog.model.vo.DashboardVo;
import com.wblog.model.vo.SystemLogVo;
import com.wblog.service.ArticleService;
import com.wblog.service.ColumnService;
import com.wblog.service.DashboardService;
import com.wblog.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    SystemLogService systemLogService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ColumnService columnService;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public DashboardVo getDashboardData() throws ExecutionException, InterruptedException {
        DashboardVo dashboardVo = new DashboardVo();

        CompletableFuture<Void> viewNumFuture = CompletableFuture.runAsync(() -> {
            String s = redisTemplate.opsForValue().get(BlogConstant.BLOG_VIEW_COUNT);
            dashboardVo.setViewNum(StringUtils.isEmpty(s) ? "0" : s);
            log.info("管理端：异步查询访问数量");
        }, executor);

        CompletableFuture<Void> articleNumFuture = CompletableFuture.runAsync(() -> {
            int count = articleService.count(new QueryWrapper<ArticleEntity>().in("state", ArticleStateEnum.PUBLIC.getCode(), ArticleStateEnum.SECRET.getCode()));
            dashboardVo.setArticleNum(count);
            log.info("管理端：异步查询文章数量");
        }, executor);
        CompletableFuture<Void> columnNumFuture = CompletableFuture.runAsync(() -> {
            int count = columnService.count(new QueryWrapper<ColumnEntity>());
            dashboardVo.setColumnNum(count);
            log.info("管理端：异步查询专栏数量");
        }, executor);
        CompletableFuture.allOf(viewNumFuture, articleNumFuture, columnNumFuture).get();
        return dashboardVo;
    }
}
