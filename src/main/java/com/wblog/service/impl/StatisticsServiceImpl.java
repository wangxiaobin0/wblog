package com.wblog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.common.utils.DateUtils;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.entity.StatisticsEntity;
import com.wblog.model.vo.ArticleStatisticsVo;
import com.wblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.StatisticsDao;
import com.wblog.service.StatisticsService;


@Service("statisticsService")
public class StatisticsServiceImpl extends ServiceImpl<StatisticsDao, StatisticsEntity> implements StatisticsService {


    @Autowired
    ArticleService articleService;

    @Override
    public void articleStatisticsTask() {
        LocalDate today = LocalDate.now();
        //统计昨天的
        LocalDate yesterday = today.plusDays(-1);
        String statisticsDate = DateUtils.formatLocalDate(yesterday, "yyyy-MM-dd");
        //获取昨天的数量
        int count = articleService.count(new QueryWrapper<ArticleEntity>().like("create_time", statisticsDate));

        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setDate(statisticsDate);
        statisticsEntity.setCount(count);
        this.save(statisticsEntity);
    }

    @Override
    public void initialize() {
        LocalDate now = LocalDate.now();
        LocalDate lastYear = now.plusYears(-1);
        Date today = new Date();
        List<StatisticsEntity> statisticsEntities = new ArrayList<>();
        //添加一年的数据，不添加今天
        while (ChronoUnit.DAYS.between(lastYear, now) > 0) {
            String localDate = DateUtils.formatLocalDate(lastYear, "yyyy-MM-dd");
            StatisticsEntity statisticsEntity = new StatisticsEntity(localDate, 0, today);
            statisticsEntities.add(statisticsEntity);
            lastYear = lastYear.plusDays(1);
        }
        this.saveBatch(statisticsEntities);
    }

    @Override
    public String articleStatistics() throws JsonProcessingException {
        List<StatisticsEntity> list = this.list(new QueryWrapper<StatisticsEntity>().orderByDesc("date").last("limit 365"));
        Map<Long, Integer> collect = list.stream().collect(Collectors.toMap(k -> DateUtils.parseDate(k.getDate(), "yyyy-MM-dd").getTime() / 1000, StatisticsEntity::getCount));
        LinkedHashMap<Long, Integer> result = new LinkedHashMap<>();
        collect.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            result.put(entry.getKey(), entry.getValue());
        });
        ObjectMapper objectMapper = new ObjectMapper();
        String asString = objectMapper.writeValueAsString(result);
        return asString;
    }

    public void his() {
        LocalDate now = LocalDate.now();
        LocalDate lastYear = now.plusDays(-30);
        Date today = new Date();
        List<StatisticsEntity> statisticsEntities = new ArrayList<>();
        List<ArticleEntity> list = articleService.list();
        //添加一年的数据，不添加今天
        while (ChronoUnit.DAYS.between(lastYear, now) > 0) {
            String localDate = DateUtils.formatLocalDate(lastYear, "yyyy-MM-dd");
            int count = (int) (list.stream()
                    .filter(articleEntity -> DateUtils.formatDate(articleEntity.getCreateTime(), "yyyy-MM-dd").equalsIgnoreCase(localDate))
                    .count());
            StatisticsEntity statisticsEntity = new StatisticsEntity(localDate, count, today);
            statisticsEntities.add(statisticsEntity);
            lastYear = lastYear.plusDays(1);
        }
        this.updateBatchById(statisticsEntities);
    }


}