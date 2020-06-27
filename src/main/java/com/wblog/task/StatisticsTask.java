package com.wblog.task;

import com.wblog.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计定时任务
 */
@Slf4j
@Component
public class StatisticsTask {

    @Autowired
    StatisticsService statisticsService;


    public void articleStatisticsTask() {
        statisticsService.articleStatisticsTask();
    }
}
