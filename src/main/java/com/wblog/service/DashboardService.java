package com.wblog.service;

import com.wblog.model.vo.DashboardVo;

import java.util.concurrent.ExecutionException;

/**
 *
 */
public interface DashboardService {

    DashboardVo getDashboardData() throws ExecutionException, InterruptedException;
}
