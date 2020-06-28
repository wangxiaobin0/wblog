package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.interceptor.UserRequestInterceptor;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.UserTo;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.model.vo.ColumnVo;
import com.wblog.service.ColumnItemService;
import com.wblog.service.ColumnRedisService;
import lombok.extern.slf4j.Slf4j;
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
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ColumnEntity> page = this.page(
                new Query<ColumnEntity>().getPage(params),
                new QueryWrapper<ColumnEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void add(ColumnEntity column) {
        AdminTo adminTo = ThreadLocalUtils.getAdminTo();
        column.setAdminId(adminTo.getAdminId());
        this.save(column);
        log.info("新增博客。{}", column);
    }

    @Override
    public ColumnDetailVo columnDetail(Long id) throws ExecutionException, InterruptedException {
        ColumnDetailVo detailVo = new ColumnDetailVo();

        /**
         * 查询专栏信息
         */
        CompletableFuture<Void> columnFuture = CompletableFuture.runAsync(() -> {
            ColumnEntity columnEntity = this.getById(id);
            BeanUtils.copyProperties(columnEntity, detailVo);
        }, executor);

        /**
         * 查询专栏订阅人数
         */
        CompletableFuture<Void> subscribeNum = CompletableFuture.runAsync(() -> {
            Long count = columnRedisService.getCount(id);
            detailVo.setSubscribeNum(count);
        }, executor);


        /**
         * 查询专栏内文章
         */
        CompletableFuture<Void> itemFuture = CompletableFuture.runAsync(() -> {

            List<ColumnItemVo> itemVos = columnItemService.getColumnItems(id);
            detailVo.setItemVos(itemVos);
        }, executor);

        UserTo userTo = ThreadLocalUtils.getUserTo();
        //是否收藏，仅用于访客
        CompletableFuture<Void> hasSubscribeFuture = CompletableFuture.runAsync(() -> {
            //访客id不为null，再查询是否收藏
            if (userTo != null) {
                Boolean hasSubscribe = columnRedisService.hasSubscribe(id, userTo.getUserKey());
                detailVo.setHasSubscribe(hasSubscribe);
            }
        }, executor);

        //四个异步任务执行结束后再继续执行
        CompletableFuture.allOf(columnFuture, subscribeNum, itemFuture, hasSubscribeFuture).get();
        return detailVo;
    }

    @Override
    public List<ColumnVo> columnList() {
        //查询专栏列表
        List<ColumnVo> entityList = this.baseMapper.columnList();
        List<ColumnVo> list = entityList.stream().map(columnVo -> {
            //查询专栏订阅数
            Long count = columnRedisService.getCount(columnVo.getId());
            columnVo.setCollectNum(count);
            return columnVo;
        }).collect(Collectors.toList());
        return list;
    }

}