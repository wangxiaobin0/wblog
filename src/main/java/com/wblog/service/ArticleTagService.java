package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wblog.common.utils.PageUtils;
import com.wblog.model.entity.ArticleTagEntity;
import com.wblog.model.vo.TagStatisticsVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author wangxb
 * @email 
 */
public interface ArticleTagService extends IService<ArticleTagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TagStatisticsVo> queryTagStatistics(Set<String> keys);
}

