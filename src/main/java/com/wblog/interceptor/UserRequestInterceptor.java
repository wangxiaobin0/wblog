package com.wblog.interceptor;

import com.wblog.common.constant.AuthConstant;
import com.wblog.common.constant.SessionConstant;
import com.wblog.model.entity.UserEntity;
import com.wblog.model.to.BloggerTo;
import com.wblog.model.to.UserTo;
import com.wblog.model.vo.TagVo;
import com.wblog.service.AdminProfileService;
import com.wblog.service.AdminService;
import com.wblog.service.TagService;
import com.wblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class UserRequestInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<UserTo> threadLocal = new ThreadLocal<>();

    @Autowired
    UserService userService;

    @Autowired
    AdminProfileService adminProfileService;

    @Autowired
    TagService tagService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //保存博主信息到session中
        HttpSession session = request.getSession();
        Object blogger = session.getAttribute(SessionConstant.SESSION_BLOGGER);
        if (blogger == null) {
            BloggerTo bloggerInfo = adminProfileService.getBloggerInfo();
            session.setAttribute(SessionConstant.SESSION_BLOGGER, bloggerInfo);
        }
        Object tagsObj = session.getAttribute(SessionConstant.SESSION_BLOG_TAG);
        if (tagsObj == null) {
            List<TagVo> tags = tagService.getIndexTagList();
            session.setAttribute(SessionConstant.SESSION_BLOG_TAG, tags);
        }
        //cookie中的访客信息
        UserTo userTo = getUserToFromCookie(request);
        if (userTo == null) {
            userTo = new UserTo();
            String userKey = UUID.randomUUID().toString();
            userTo.setUserKey(userKey);

            userService.addNewUser(userKey);
        }
        threadLocal.set(userTo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Cookie userKeyCookie = new Cookie(AuthConstant.USER_COOKIE_KEY, getUser().getUserKey());
        userKeyCookie.setDomain(AuthConstant.USER_COOKIE_DOMAIN);
        userKeyCookie.setHttpOnly(true);
        userKeyCookie.setMaxAge(AuthConstant.USER_COOKIE_MAX_AGE);
        response.addCookie(userKeyCookie);
    }

    public static UserTo getUser() {
        return threadLocal.get();
    }

    /**
     * 从cookies中获取user-key
     * @param request
     * @return
     */
    private UserTo getUserToFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (AuthConstant.USER_COOKIE_KEY.equals(cookie.getName())) {
                UserTo userTo = new UserTo();
                userTo.setUserKey(cookie.getValue());
                return userTo;
            }
        }
        return null;
    }
}
