package com.wblog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.interceptor.UserRequestInterceptor;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.UserTo;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnIndexVo;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.model.vo.ColumnVo;
import com.wblog.service.ColumnItemService;
import com.wblog.service.ColumnRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ColumnDao;
import com.wblog.model.entity.ColumnEntity;
import com.wblog.service.ColumnService;


@Slf4j
@Service("columnService")
public class ColumnServiceImpl extends ServiceImpl<ColumnDao, ColumnEntity> implements ColumnService {

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    ColumnItemService columnItemService;

    @Autowired
    ColumnRedisService columnRedisService;

    @Override
    public PageResult listByPage(Long page, Long size) {
        log.info("管理端：分页查询专栏列表");
        Page<ColumnEntity> iPage = new Page<>(page, size);
        QueryWrapper<ColumnEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        IPage<ColumnEntity> pageResult = this.page(iPage, queryWrapper);
        return new PageResult(pageResult);
    }

    @Override
    public void add(ColumnEntity column) {
        AdminTo adminTo = ThreadLocalUtils.getAdminTo();
        column.setAdminId(adminTo.getAdminId());
        this.save(column);
        log.info("管理端：新增博客。{}", column);
    }

    @Override
    public ColumnDetailVo columnDetail(Long id) throws ExecutionException, InterruptedException {
        log.info("查询专栏详情流程开始");
        ColumnDetailVo detailVo = new ColumnDetailVo();

        /**
         * 查询专栏信息
         */
        CompletableFuture<Void> columnFuture = CompletableFuture.runAsync(() -> {
            ColumnEntity columnEntity = this.getById(id);
            BeanUtils.copyProperties(columnEntity, detailVo);
            log.info("查询专栏信息");
        }, executor);

        /**
         * 查询专栏订阅人数
         */
        CompletableFuture<Void> subscribeNum = CompletableFuture.runAsync(() -> {
            Long count = columnRedisService.getCount(id);
            detailVo.setSubscribeNum(count);
            log.info("查询专栏订阅人数");
        }, executor);


        UserTo userTo = ThreadLocalUtils.getUserTo();
        //是否收藏，仅用于访客
        CompletableFuture<Void> hasSubscribeFuture = CompletableFuture.runAsync(() -> {
            //访客id不为null，再查询是否收藏
            if (userTo != null) {
                Boolean hasSubscribe = columnRedisService.hasSubscribe(id, userTo.getUserKey());
                detailVo.setHasSubscribe(hasSubscribe);
                log.info("访客端：查询访客{}是否订阅该专栏", userTo.getUserKey());
            }
        }, executor);
        //三个异步任务执行结束后再继续执行
        CompletableFuture.allOf(columnFuture, subscribeNum, hasSubscribeFuture).get();
        log.info("查询专栏详情流程结束");
        return detailVo;
    }

    @Override
    public ColumnIndexVo columnList(Long page, Long size) throws ExecutionException, InterruptedException {
        log.info("访客：{}查询专栏列表流程开始。page：{}，size：{}", ThreadLocalUtils.getUserTo().getUserKey(), page, size);
        ColumnIndexVo columnItemVo = new ColumnIndexVo();

        CompletableFuture<Void> columnFuture = CompletableFuture.runAsync(() -> {
            //查询专栏列表
            PageHelper.startPage(page.intValue(), size.intValue());
            List<ColumnVo> entityList = this.baseMapper.columnList();
            PageInfo<ColumnVo> pageInfo = new PageInfo<>(entityList);
            columnItemVo.setPageResult(new PageResult(pageInfo));
            log.info("查询专栏列表");
        }, executor);

        CompletableFuture<Void> bannerFuture = CompletableFuture.runAsync(() -> {
            QueryWrapper<ColumnEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id", "image");
            queryWrapper.eq("banner", true);
            queryWrapper.comment("limit 5");
            List<ColumnEntity> list = this.list(queryWrapper);
            List<ColumnVo> bannerList = list.stream().map(columnEntity -> {
                ColumnVo vo = new ColumnVo();
                BeanUtils.copyProperties(columnEntity, vo);
                return vo;
            }).collect(Collectors.toList());
            columnItemVo.setBannerList(bannerList);
            log.info("查询专栏banner位");
        }, executor);
        CompletableFuture.allOf(columnFuture, bannerFuture).get();
        return columnItemVo;
    }

    @Override
    public void addOrCancelBanner(Long columnId, Boolean flag) {
        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setId(columnId);
        columnEntity.setBanner(flag);
        this.updateById(columnEntity);
    }

    @Override
    public List<ColumnVo> unAddColumn(Long articleId) {
        List<ColumnVo> columnVoList = this.baseMapper.unAddColumn(articleId);
        return columnVoList;
    }

}