package com.wblog.controller.admin;

import com.wblog.annotation.SysLog;
import com.wblog.common.constant.AuthConstant;
import com.wblog.common.constant.SessionConstant;
import com.wblog.common.utils.R;
import com.wblog.exception.AuthException;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.vo.LoginVo;
import com.wblog.model.vo.RegisterVo;
import com.wblog.service.AdminProfileService;
import com.wblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    AdminProfileService adminProfileService;

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String goToLoginPage(@RequestParam(value = "returnUrl", required = false) String returnUrl,
                                HttpServletRequest request,
                                Model model) {
        log.info("跳转登录/注册页面流程开始。");

        HttpSession session = request.getSession();
        AdminTo adminTo = (AdminTo) session.getAttribute(SessionConstant.SESSION_LOGIN_USER);
        if (adminTo != null) {
            log.info("{}已登录，跳转首页", adminTo.getAdminId());
            return "redirect:/admin/dashboard";
        }
        AdminProfileEntity one = adminProfileService.getOne(null);
        if (one == null) {
            log.info("尚未注册，跳转注册页面");
            log.info("跳转登录/注册页面流程结束。");
            return "admin/register";
        } else {
            model.addAttribute("admin", one.getAdminId());
            model.addAttribute(AuthConstant.RETURN_URL, returnUrl);
            log.info("已注册，跳转登录页面");
            log.info("跳转登录/注册页面流程结束。");
            return "admin/login";
        }
    }

    @SysLog(business = "admin注册")
    @PostMapping("/auth/register")
    @ResponseBody
    public R register(@Valid RegisterVo registerVo) {
        log.info("注册流程开始。{}", registerVo);
        try {
            adminService.register(registerVo);
            log.info("注册流程结束。");
            return R.ok();
        } catch (Exception e) {
            throw new AuthException("注册失败");
        }
    }

    @SysLog(business = "admin登录")
    @PostMapping("/auth/login")
    @ResponseBody
    public R login(@Valid LoginVo loginVo, HttpServletRequest request) {
        log.info("登录流程开始。{}", loginVo);
        AdminTo adminTo = adminService.login(loginVo);
        log.info("设置session信息{}", adminTo);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.SESSION_LOGIN_USER, adminTo);
        return R.ok();
    }

    @SysLog(business = "退出登录")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionConstant.SESSION_LOGIN_USER);
        return "redirect:/admin";
    }
}
