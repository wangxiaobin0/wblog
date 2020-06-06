package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleTagEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleTagService extends IService<ArticleTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

