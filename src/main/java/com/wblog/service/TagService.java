package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.TagEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface TagService extends IService<TagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TagEntity> listTagByArticleId(Long articleId);
}

