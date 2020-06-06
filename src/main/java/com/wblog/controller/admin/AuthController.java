package com.wblog.controller.admin;

import com.wblog.common.utils.R;
import com.wblog.exception.AuthException;
import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.model.vo.RegisterVo;
import com.wblog.service.AdminProfileService;
import com.wblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    AdminProfileService adminProfileService;

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String goToLoginPage(@RequestParam(value = "returnUrl", required = false) String returnUrl, Model model) {
        AdminProfileEntity one = adminProfileService.getOne(null);
        if (one == null) {
            return "admin/register";
        } else {
            model.addAttribute("admin", one.getAdminId());
            return "admin/login";
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public R register(RegisterVo registerVo) {
        try {
            adminService.register(registerVo);
            return R.ok();
        } catch (Exception e) {
            throw new AuthException("注册失败");
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public R login() {
        return R.ok();
    }
}
