package com.wblog.common.constant;

import lombok.Data;

/**
 * 认证相关常量
 */
@Data
public class AuthConstant {
    /**
     * session中保存的登录用户信息
     */
    public static final String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";

    public static final String LOGIN_URL = "http://localhost/admin";

    public static final String USER_KEY = "USER_KEY";
}
