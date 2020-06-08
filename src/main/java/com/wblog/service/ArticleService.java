package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.vo.ArticlePostVo;
import com.wblog.model.vo.ArticleShowVo;

import java.util.Map;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 查询已发布文章
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询草稿箱文章
     * @param params
     * @return
     */
    PageUtils listDraft(Map<String, Object> params);

    /**
     * 查询回收站文章
     * @param params
     * @return
     */
    PageUtils listTrash(Map<String, Object> params);

    /**
     * 保存文章
     * @param article
     */
    void save(ArticlePostVo article);

    void updateTop(Long id, Boolean top);

    ArticleShowVo preview(ArticlePostVo articlePostVo);

    void updateState(Long id, Integer state);
}

