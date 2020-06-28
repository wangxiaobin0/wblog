package com.wblog.controller.user;

import com.wblog.model.vo.SystemLogVo;
import com.wblog.service.SystemLogService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangxb
 * @email
 */
@Controller
@RequestMapping("admin/log")
public class SystemLogController {

    @Autowired
    SystemLogService systemLogService;


    @GetMapping("/list")
    public String log(Model model) {
        List<SystemLogVo> logs = systemLogService.getLog();
        model.addAttribute("logs", logs);
        return "/admin/fragment/history :: history";
    }

}