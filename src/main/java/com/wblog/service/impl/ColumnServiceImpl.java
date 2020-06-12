package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.to.AdminTo;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.service.ColumnItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

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
        AdminTo adminTo = AdminRequestInterceptor.getAdmin();
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
         * 查询专栏内文章
         */
        CompletableFuture<Void> itemFuture = CompletableFuture.runAsync(() -> {

            List<ColumnItemVo> itemVos = columnItemService.getColumnItems(id);
            detailVo.setItemVos(itemVos);
        }, executor);

        //两个异步任务执行结束后再继续执行
        CompletableFuture.allOf(columnFuture, itemFuture).get();

        return detailVo;
    }

}