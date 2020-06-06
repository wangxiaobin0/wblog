package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleFileEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleFileService extends IService<ArticleFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

