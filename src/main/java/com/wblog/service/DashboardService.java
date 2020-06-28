package com.wblog.service;

import com.wblog.model.vo.SystemLogVo;

import java.util.List;

/**
 *
 */
public interface DashboardService {

    List<SystemLogVo> getDashboardData();
}
