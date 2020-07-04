package com.wblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.to.ArticleMQTo;
import com.wblog.model.vo.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 查询已发布文章
     * @param page 页码
     * @param size 显示数量
     * @return
     */
    PageResult listPublish(Long page, Long size);
    /**
     * 查询草稿箱文章
     * @param page 页码
     * @param size 显示数量
     * @param state 状态
     * @return
     */
    PageResult listDraftOrTrash(Long page, Long size, ArticleStateEnum state);

    /**
     * 保存文章
     * @param article
     */
    void save(ArticlePostVo article);

    void updateTop(Long id, Boolean top);

    ArticlePreviewVo preview(ArticlePostVo articlePostVo);

    void updateState(Long id, Integer state);

    PageResult indexList(Long page, Long size);

    ArticleItemVo getItem(Long articleId);


    void deleteExpireArticle(ArticleMQTo articleMQTo);

    UserViewVo getUserArticleList(String keyPrefix) throws ExecutionException, InterruptedException;

    void delete(Long id);

    ArticleItemVo getDetail(Long articleId);
}

