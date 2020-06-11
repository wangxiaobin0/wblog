package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.TagEntity;
import com.wblog.model.vo.TagVo;

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

    Boolean updateArticleNumById(Long id);

    void deleteByIds(Long id);

    List<TagVo> getIndexTagList();
}

