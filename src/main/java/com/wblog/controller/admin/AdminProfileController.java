package com.wblog.controller.admin;

import java.util.Arrays;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.constant.AuthConstant;
import com.wblog.common.constant.SessionConstant;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.to.AdminTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.service.AdminProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author wangxb
 * @email
 */
@Controller
@RequestMapping("admin/profile")
public class AdminProfileController {

    @Autowired
    private AdminProfileService adminProfileService;

    /**
     * 信息
     */
    @GetMapping
    public String info() {
        return "admin/profile/profile";
    }

    @GetMapping("/edit")
    public String goToEditPage() {
        return "admin/profile/edit";
    }

    @SysLog(business = "更新个人资料")
    @PostMapping
    public String updateProfile(AdminProfileEntity profileEntity, HttpServletRequest request) {
        AdminTo adminTo = adminProfileService.updateProfile(profileEntity);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.SESSION_LOGIN_USER, adminTo);
        return "redirect:/admin/profile";
    }

    @ResponseBody
    @PostMapping("/img")
    public R updateImg(@RequestParam("key") String key, @RequestParam("url") String url, HttpServletRequest request) {
        AdminTo adminTo = adminProfileService.updateImg(key, url);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.SESSION_LOGIN_USER, adminTo);
        return R.ok();
    }
}
