package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ColumnEntity;
import com.wblog.model.vo.ColumnDetailVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ColumnService extends IService<ColumnEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void add(ColumnEntity column);

    ColumnDetailVo columnDetail(Long id) throws ExecutionException, InterruptedException;
}

