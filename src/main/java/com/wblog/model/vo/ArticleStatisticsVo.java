package com.wblog.model.vo;

import com.wblog.model.entity.StatisticsEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 文章统计Vo
 */
@Data
public class ArticleStatisticsVo {

    private String startDate;

    private List<StatisticsEntity> data;
}
