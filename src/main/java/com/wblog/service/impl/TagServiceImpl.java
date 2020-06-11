package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.model.vo.TagVo;
import com.wblog.service.ArticleTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.TagDao;
import com.wblog.model.entity.TagEntity;
import com.wblog.service.TagService;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Autowired
    ArticleTagService articleTagService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagEntity> page = this.page(
                new Query<TagEntity>().getPage(params),
                new QueryWrapper<TagEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TagEntity> listTagByArticleId(Long articleId) {
        List<TagEntity> tags = this.baseMapper.listTagByArticleId(articleId);
        return tags;
    }

    @Override
    public Boolean updateArticleNumById(Long id) {
        return this.baseMapper.updateArticleNumById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(Long id) {
        this.removeById(id);
        log.info("删除标签。id{}", id);
        articleTagService.remove(new QueryWrapper<ArticleTagEntity>().eq("tag_id", id));
        log.info("删除标签{}与文章的关联关系", id);
    }

    @Override
    public List<TagVo> getIndexTagList() {
        //查询所有tag信息
        List<TagEntity> list = this.list();
        //转换为tagId
        return list.stream().map(tagEntity -> {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tagEntity, tagVo);
            return tagVo;
        }).collect(Collectors.toList());
    }


}