package com.wblog.service.impl;

import com.wblog.model.vo.SystemLogVo;
import com.wblog.service.DashboardService;
import com.wblog.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    SystemLogService systemLogService;

    @Override
    public List<SystemLogVo> getDashboardData() {
        List<SystemLogVo> log = systemLogService.getLog();
        return log;
    }
}
