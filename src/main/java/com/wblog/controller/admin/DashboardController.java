package com.wblog.controller.admin;

import com.wblog.model.vo.DashboardVo;
import com.wblog.model.vo.SystemLogVo;
import com.wblog.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) throws ExecutionException, InterruptedException {
        DashboardVo dashboardData = dashboardService.getDashboardData();
        model.addAttribute("dashboardData", dashboardData);
        return "admin/dashboard";
    }
}
