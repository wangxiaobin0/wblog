package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ColumnItemEntity;
import com.wblog.model.vo.ColumnItemVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ColumnItemService extends IService<ColumnItemEntity> {
    PageUtils queryPage(Map<String, Object> params);

    PageResult queryColumnItemByPage(Long id, Long page, Long size);

    void addToColumn(ColumnItemEntity columnItem);

    Boolean changeSort(Long id, Boolean sort);

    List<ColumnItemVo> getUnAddArticle(Long columnId);
}

