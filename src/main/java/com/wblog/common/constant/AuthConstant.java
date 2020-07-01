package com.wblog.common.constant;

import lombok.Data;

/**
 * 认证相关常量
 */
@Data
public class AuthConstant {

    /**
     * 登录链接
     */
    public static final String LOGIN_URL = "http://localhost/admin?returnUrl=";

    /**
     * 页面重定向
     */
    public static final String RETURN_URL = "returnUrl";

    /**
     * 访客cookie的key
     */
    public static final String USER_COOKIE_KEY = "USER_KEY";
    /**
     * 访客cookie的domain
     */
    public static final String USER_COOKIE_DOMAIN = "";

    /**
     * 访客cookie生命,30天
     */
    public static final Integer USER_COOKIE_MAX_AGE = 60 * 60 * 24 * 30;

    public static final String DEFAULT_AVATAR = "/static/assets/img/default.png";
}
