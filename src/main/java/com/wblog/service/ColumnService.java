package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ColumnEntity;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ColumnService extends IService<ColumnEntity> {

    PageResult listByPage(Long page, Long size);

    void add(ColumnEntity column);

    ColumnDetailVo columnDetail(Long id) throws ExecutionException, InterruptedException;

    List<ColumnVo> columnList();

    void addOrCancelBanner(Long columnId, Boolean flag);

    List<ColumnVo> unAddColumn(Long articleId);
}

