package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.vo.ArticlePostVo;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleService extends IService<ArticleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ArticlePostVo article);

    PageUtils listDraft(Map<String, Object> params);

    PageUtils listTrash(Map<String, Object> params);
}

