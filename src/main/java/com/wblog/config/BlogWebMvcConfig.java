package com.wblog.config;

import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.interceptor.UserRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BlogWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    AdminRequestInterceptor adminRequestInterceptor;

    @Autowired
    UserRequestInterceptor userRequestInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //为后台管理添加请求拦截器
        registry.addInterceptor(adminRequestInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin", "/auth/**", "/error");

        //为游客访问添加请求拦截器
        registry.addInterceptor(userRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/**", "/auth/**", "/error");
    }
}
