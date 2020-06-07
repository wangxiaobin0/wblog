package com.wblog.service.impl;

import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.to.AdminTo;
import com.wblog.model.vo.ArticlePostVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleDao;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.service.ArticleService;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                new QueryWrapper<ArticleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void save(ArticlePostVo article) {
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(article, articleEntity);
        AdminTo adminTo = AdminRequestInterceptor.getAdmin();
        articleEntity.setAdminId(adminTo.getAdminId());

        this.save(articleEntity);
    }

    @Override
    public PageUtils listDraft(Map<String, Object> params) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleStateEnum.DRAFT.getCode());
        queryWrapper.orderByDesc("update_time");
        IPage<ArticleEntity> page = this.page(new Query<ArticleEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public PageUtils listTrash(Map<String, Object> params) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleStateEnum.DELETE.getCode());
        queryWrapper.orderByDesc("update_time");
        IPage<ArticleEntity> page = this.page(new Query<ArticleEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

}