package com.wblog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.common.constant.ArticleConstant;
import com.wblog.common.constant.MQConstant;
import com.wblog.common.enume.ArticleMqEnum;
import com.wblog.common.enume.ArticleStateEnum;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.exception.ArticleException;
import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.model.entity.ColumnItemEntity;
import com.wblog.model.entity.TagEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.ArticleMQTo;
import com.wblog.model.vo.*;
import com.wblog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.ArticleDao;
import com.wblog.model.entity.ArticleEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    TagService tagService;

    @Autowired
    ArticleTagService articleTagService;

    @Autowired
    ArticleRedisService articleRedisService;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ColumnItemService columnItemService;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ArticleIndexVo> queryPage() {
        //查询状态为公开和保密的文章
        List<ArticleIndexVo> indexList = this.baseMapper.getPublishList();
        return indexList;
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
        AdminTo adminTo = ThreadLocalUtils.getAdminTo();
        articleEntity.setAdminId(adminTo.getAdminId());
        this.save(articleEntity);
        log.info("保存文章。id:{}", articleEntity.getId());


        List<TagEntity> tagEntities = tagService.list();
        String[] tags = article.getTag().split("/");
        //key为tag名，value为id
        Map<String, Long> tagMap = tagEntities.stream().collect(Collectors.toMap(TagEntity::getName, TagEntity::getId));
        for (String tag : tags) {
            if (StringUtils.isEmpty(tag)) {
                break;
            }
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

        //如果保存的是草稿箱消息，发送MQ
        if (articleEntity.getState() == ArticleStateEnum.DRAFT.getCode()) {
            rabbitMqService.sendMessage(
                    MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE,
                    MQConstant.ArticleConstant.ARTICLE_DRAFT_DELAY_ROUTING_KEY,
                    new ArticleMQTo(articleEntity.getId(), ArticleMqEnum.DRAFT.getCode()));
            log.info("保存草稿箱文章{}，20秒自动删除", articleEntity.getId());
        } else {
            //保存到ES
            rabbitMqService.sendMessage(
                    MQConstant.SearchConstant.SEARCH_EVENT_EXCHANGE,
                    MQConstant.SearchConstant.SEARCH_ADD_ARTICLE_ROUTING_KEY,
                    new ArticleMQTo(articleEntity.getId(), ArticleMqEnum.PUBLIC.getCode()));
            log.info("保存到ES消息发送成功");
        }

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
    public ArticlePreviewVo preview(ArticlePostVo articlePostVo) {
        ArticlePreviewVo showVo = new ArticlePreviewVo();
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
        if (indexList == null) {
            return null;
        }
        List<ArticleIndexVo> indexVoList = indexList.stream().map(articleIndexVo -> {
            String abstractHtml = articleIndexVo.getAbstractHtml();
            abstractHtml = abstractHtml.replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
            articleIndexVo.setAbstractHtml(abstractHtml.substring(0, Math.min(abstractHtml.length(), 160)) + "...");
            return articleIndexVo;
        }).collect(Collectors.toList());
        return getArticleIndexListWithCount(indexVoList);
    }

    @Override
    public ArticleItemVo getItem(Long articleId) {
        //更新浏览数,添加浏览记录
        articleRedisService.incrViewCountAndAddViewHistory(articleId);
        //查询数据库文章信息
        ArticleItemVo articleItem = getDetail(articleId);

        String userKey = ThreadLocalUtils.getUserTo().getUserKey();
        //当前访客是否收藏过
        articleItem.setHasCollect(articleRedisService.orNot(articleId, ArticleConstant.ARTICLE_COLLECT, userKey));

        //是否赞过
        articleItem.setHasThumbUp(articleRedisService.orNot(articleId, ArticleConstant.ARTICLE_THUMB_UP, userKey));

        return articleItem;
    }

    @Override
    public List<ArticleIndexVo> getPublishList() {
        return this.baseMapper.getPublishList();
    }

    @Override
    public void deleteExpireArticle(ArticleMQTo articleMQTo) {
        ArticleEntity byId = this.getById(articleMQTo.getId());
        //文章不存在算处理成功
        if (byId == null) {
            log.info("文章{}不存在", articleMQTo.getId());
            return ;
        }
        //文章状态不是草稿箱或回收站
        if (byId.getState() != ArticleStateEnum.DRAFT.getCode() &&
                byId.getState() != ArticleStateEnum.TRASH.getCode()) {
            log.info("文章{}状态已变更", byId.getId());
        }
        Integer count = this.baseMapper.deleteExpireArticle(articleMQTo.getId(), articleMQTo.getState());
        if (count != 1) {
            throw new ArticleException("文章删除失败");
        }
    }

    @Override
    public UserViewVo getUserArticleList(String keyPrefix) throws ExecutionException, InterruptedException {
        UserViewVo vo = new UserViewVo();
        Set<String> keys = articleRedisService.getUserView(keyPrefix);
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        //异步查询文章列表
        CompletableFuture<Void> likeListFuture = CompletableFuture.runAsync(() -> {
            List<Long> ids = keys.stream().map(key -> Long.parseLong(key)).collect(Collectors.toList());
            List<ArticleEntity> articleEntities = this.list(new QueryWrapper<ArticleEntity>().in("id", ids).orderByDesc("create_time"));
            List<ArticleIndexVo> collect = articleEntities.stream().map(articleEntity -> {
                ArticleIndexVo indexVo = new ArticleIndexVo();
                BeanUtils.copyProperties(articleEntity, indexVo);
                return indexVo;
            }).collect(Collectors.toList());
            vo.setArticleList(collect);
        }, executor);

        //异步查询tag数据
        CompletableFuture<Void> tagFuture = CompletableFuture.runAsync(() -> {
            List<TagStatisticsVo> tagVo = articleTagService.queryTagStatistics(keys);
            try {
                vo.setTagJson(objectMapper.writeValueAsString(tagVo));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }, executor);
        CompletableFuture.allOf(likeListFuture, tagFuture).get();
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //更新文章状态为回收站
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(id);
        articleEntity.setState(ArticleStateEnum.TRASH.getCode());
        this.updateById(articleEntity);

        //删除文章与标签的对应关系
        articleTagService.remove(new QueryWrapper<ArticleTagEntity>().eq("article_id", id));

        //删除文章与专栏的对应关系
        columnItemService.remove(new QueryWrapper<ColumnItemEntity>().eq("article_id", id));

        //延时删除文章
        rabbitMqService.sendMessage(
                MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE,
                MQConstant.ArticleConstant.ARTICLE_DRAFT_DELAY_ROUTING_KEY,
                new ArticleMQTo(id, ArticleMqEnum.TRASH.getCode()));
        //发送消息删除ES中的文章
        rabbitMqService.sendMessage(
                MQConstant.SearchConstant.SEARCH_EVENT_EXCHANGE,
                MQConstant.SearchConstant.SEARCH_DELETE_ARTICLE_ROUTING_KEY,
                new ArticleMQTo(articleEntity.getId(), ArticleMqEnum.DELETE.getCode()));
    }

    @Override
    public ArticleItemVo getDetail(Long articleId) {
        //查询数据库文章信息
        ArticleItemVo articleItem = this.baseMapper.getArticleItem(articleId);

        //TODO:使用Assert处理异常
        if (articleItem == null) {
            throw new ArticleException("文章不存在");
        }
        //查询浏览数
        Long viewCount = articleRedisService.getCount(articleId, ArticleConstant.ARTICLE_VIEW_COUNT);
        articleItem.setViewNum(viewCount);

        //查询收藏数
        Long collectCount = articleRedisService.getCount(articleId, ArticleConstant.ARTICLE_COLLECT);
        articleItem.setCollectNum(collectCount);

        //查询点赞数
        Long thumbUpCount = articleRedisService.getCount(articleId, ArticleConstant.ARTICLE_THUMB_UP);
        articleItem.setThumbUp(thumbUpCount);

        log.info("获取文章结果：{}", articleItem);
        return articleItem;
    }

    /**
     * 查询列表中各个文章的浏览/收藏/点赞数
     * @param publishList
     * @return
     */
    private List<ArticleIndexVo> getArticleIndexListWithCount(List<ArticleIndexVo> publishList) {
        if (publishList == null) {
            return null;
        }
        return publishList.stream().peek(articleIndexVo -> {
            //查询浏览数
            Long viewCount = articleRedisService.getCount(articleIndexVo.getId(), ArticleConstant.ARTICLE_VIEW_COUNT);
            articleIndexVo.setViewCount(viewCount);

            //查询点赞数
            Long thumpUpCount = articleRedisService.getCount(articleIndexVo.getId(), ArticleConstant.ARTICLE_THUMB_UP);
            articleIndexVo.setThumbUpCount(thumpUpCount);

            //查询收藏数
            Long collectCount = articleRedisService.getCount(articleIndexVo.getId(), ArticleConstant.ARTICLE_COLLECT);
            articleIndexVo.setCollectNum(collectCount);
        }).collect(Collectors.toList());
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