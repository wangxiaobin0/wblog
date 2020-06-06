package com.wblog.interceptor;

import com.wblog.common.constant.AuthConstant;
import com.wblog.model.entity.UserEntity;
import com.wblog.model.to.UserTo;
import com.wblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration
public class UserRequestInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<UserTo> threadLocal = new ThreadLocal<>();

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserTo userTo = getUserToFromCookie(request);

        if (userTo == null) {
            userTo = new UserTo();
            String userKey = UUID.randomUUID().toString();
            userTo.setUserKey(userKey);

            //保存访客信息到数据库
            UserEntity userEntity = new UserEntity();
            userEntity.setKey(userKey);
            userService.save(userEntity);
        }
        threadLocal.set(userTo);
        return false;
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
        for (Cookie cookie : cookies) {
            if (AuthConstant.USER_KEY.equals(cookie.getName())) {
                UserTo userTo = new UserTo();
                userTo.setUserKey(cookie.getValue());
                return userTo;
            }
        }
        return null;
    }
}
