package com.wblog.service.impl;

import com.wblog.common.constant.ArticleConstant;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.enume.TagStateEnum;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.exception.ArticleException;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.model.entity.TagEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.vo.*;
import com.wblog.service.ArticleRedisService;
import com.wblog.service.ArticleTagService;
import com.wblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleDao;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.service.ArticleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.text.html.HTML;


@Slf4j
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    TagService tagService;
    @Autowired
    ArticleTagService articleTagService;

    @Autowired
    ArticleRedisService articleRedisService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        //查询状态为公开和保密的文章
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleStateEnum.PUBLIC.getCode());
        queryWrapper.or().eq("state", ArticleStateEnum.SECRET.getCode());
        queryWrapper.orderByDesc("update_time");
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                queryWrapper
        );
        //查询文章的标签
        List<ArticleEntity> records = page.getRecords();
        List<ArticleEntity> articleEntities = records.stream().map(articleEntity -> {
            List<TagEntity> tagEntities = tagService.listTagByArticleId(articleEntity.getId());
            articleEntity.setTags(tagEntities);
            return articleEntity;
        }).collect(Collectors.toList());
        page.setRecords(articleEntities);
        return new PageUtils(page);
    }

    @Override
    public PageUtils listDraft(Map<String, Object> params) {
        //查询草稿箱
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleStateEnum.DRAFT.getCode());
        queryWrapper.orderByDesc("update_time");
        IPage page = this.page(new Query<ArticleEntity>().getPage(params), queryWrapper);
        //转为ArticleListVo
        List<ArticleEntity> records = page.getRecords();
        List<ArticleListVo> articleListVos = records.stream().map(articleEntity -> {
            ArticleListVo articleListVo = new ArticleListVo();
            BeanUtils.copyProperties(articleEntity, articleListVo);
            //计算剩余保存时间
            int remainTime = remainTime(articleEntity.getUpdateTime());
            //查询标签
            List<TagEntity> tagEntities = tagService.listTagByArticleId(articleEntity.getId());
            articleListVo.setTags(tagEntities);
            articleListVo.setRemainTime(remainTime);
            return articleListVo;
        }).collect(Collectors.toList());
        page.setRecords(articleListVos);
        return new PageUtils(page);
    }

    @Override
    public PageUtils listTrash(Map<String, Object> params) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleStateEnum.TRASH.getCode());
        queryWrapper.orderByDesc("update_time");
        IPage page = this.page(new Query<ArticleEntity>().getPage(params), queryWrapper);
        //转为ArticleListVo
        List<ArticleEntity> records = page.getRecords();
        List<ArticleListVo> articleListVos = records.stream().map(articleEntity -> {
            ArticleListVo articleListVo = new ArticleListVo();
            BeanUtils.copyProperties(articleEntity, articleListVo);
            //计算剩余保存时间
            int remainTime = remainTime(articleEntity.getUpdateTime());
            //查询标签
            List<TagEntity> tagEntities = tagService.listTagByArticleId(articleEntity.getId());
            articleListVo.setTags(tagEntities);
            articleListVo.setRemainTime(remainTime);
            return articleListVo;
        }).collect(Collectors.toList());
        page.setRecords(articleListVos);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(ArticlePostVo article) {
        //保存文章
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(article, articleEntity);
        AdminTo adminTo = AdminRequestInterceptor.getAdmin();
        articleEntity.setAdminId(adminTo.getAdminId());
        this.save(articleEntity);
        log.info("保存文章。id:{}", articleEntity.getId());


        List<TagEntity> tagEntities = tagService.list();
        String[] tags = article.getTag().split("/");
        //key为tag名，value为id
        Map<String, Long> tagMap = tagEntities.stream().collect(Collectors.toMap(TagEntity::getName, TagEntity::getId));
        for (String tag : tags) {
            Long tagId = null;
            //保存新增的tag
            if (!tagMap.containsKey(tag)) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setName(tag);
                tagService.save(tagEntity);
                tagId = tagEntity.getId();
            } else {
                tagId = tagMap.get(tag);
                tagService.updateArticleNumById(tagId);
            }
            //保存文章与tag的关系
            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setTagId(tagId);
            articleTagEntity.setArticleId(articleEntity.getId());
            articleTagService.save(articleTagEntity);
        }
        log.info("保存文章与tag的关系");
    }

    @Override
    public void updateTop(Long id, Boolean top) {
        log.info("更新博客置顶状态。id:{},top:{}", id, top);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(id);
        articleEntity.setTop(top);
        this.updateById(articleEntity);
    }

    @Override
    public ArticleShowVo preview(ArticlePostVo articlePostVo) {
        ArticleShowVo showVo = new ArticleShowVo();
        BeanUtils.copyProperties(articlePostVo, showVo);
        String[] split = articlePostVo.getTag().split("/");
        if (split.length != 1 || StringUtils.isEmpty(split[0])) {
            List<String> tags = Arrays.stream(split).map(item -> item).collect(Collectors.toList());
            showVo.setTag(tags);
        }
        return showVo;
    }

    @Override
    public void updateState(Long id, Integer state) {
        log.info("更新博客状态。id:{},state:{}", id, state);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(id);
        articleEntity.setState(state);
        this.updateById(articleEntity);
    }

    @Override
    public List<ArticleIndexVo> indexList() {
        //查询首页列表
        List<ArticleIndexVo> indexList = this.baseMapper.getIndexList();
        return indexList.stream().map(articleIndexVo -> {
            String abstractHtml = articleIndexVo.getAbstractHtml();
            abstractHtml = abstractHtml.replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
            articleIndexVo.setAbstractHtml(abstractHtml.substring(0, abstractHtml.length() > 40 ? 40 : abstractHtml.length()));
            if (articleIndexVo.getTags() != null && articleIndexVo.getTags().size() > 2) {
                while (articleIndexVo.getTags().size() != 2) {
                    articleIndexVo.getTags().remove(2);
                }
            }
            return articleIndexVo;
        }).collect(Collectors.toList());
    }

    @Override
    public ArticleItemVo getItem(Long articleId) {
        //更新浏览数
        articleRedisService.incrViewCount(articleId);
        //查询数据库文章信息
        ArticleItemVo articleItem = this.baseMapper.getArticleItem(articleId);
        if (articleItem == null) {
            throw new ArticleException("文章不存在");
        }
        //查询浏览数
        String viewCount = articleRedisService.getCollectCount(articleId, ArticleConstant.ARTICLE_VIEW_COUNT);
        articleItem.setViewNum(viewCount);
        //查询收藏数
        String collectCount = articleRedisService.getCollectCount(articleId, ArticleConstant.ARTICLE_COLLECT_COUNT);
        articleItem.setCollectNum(collectCount);
        //查询点赞数
        String thumbUpCount = articleRedisService.getCollectCount(articleId, ArticleConstant.ARTICLE_THUMB_UP);
        articleItem.setThumbUp(thumbUpCount);
        log.info("获取文章结果：{}", articleItem);
        return articleItem;
    }

    private int remainTime(Date updateTime) {
        LocalDate localDate = updateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusDays(7);
        LocalDate now = LocalDate.now();
        Period between = Period.between(now, localDate);
        int days = between.getDays();
        return days;
    }
}