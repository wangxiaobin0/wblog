package com.wblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wblog.model.entity.StatisticsEntity;
import com.wblog.model.vo.ArticleStatisticsVo;

/**
 * @author wangxb
 * @email
 */
public interface StatisticsService extends IService<StatisticsEntity> {

    void articleStatisticsTask();

    void initialize();

    String articleStatistics() throws JsonProcessingException;
}

