package com.wblog.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wblog.service.StatisticsService;
import com.wblog.service.impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author wangxb
 * @email
 */
@RestController
@RequestMapping("admin/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;


    @GetMapping("/article")
    public String articleStatistics() throws JsonProcessingException {
        String data = statisticsService.articleStatistics();
        return data;
    }
}