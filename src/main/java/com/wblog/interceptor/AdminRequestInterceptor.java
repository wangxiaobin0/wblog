package com.wblog.interceptor;

import com.wblog.common.constant.AuthConstant;
import com.wblog.common.constant.SessionConstant;
import com.wblog.model.to.AdminTo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台请求拦截器
 */
@Configuration
public class AdminRequestInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<AdminTo> threadLocal = new ThreadLocal<>();

    /**
     * 前置拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        AdminTo adminTo = (AdminTo) session.getAttribute(SessionConstant.SESSION_LOGIN_USER);

        if (adminTo == null) {
            response.sendRedirect(AuthConstant.LOGIN_URL + request.getRequestURI());
            return false;
        }
        threadLocal.set(adminTo);
        return true;
    }

    public static AdminTo getAdmin() {
        return threadLocal.get();
    }
}
