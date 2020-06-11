package com.wblog.model.to;

import lombok.Data;

/**
 * 博主信息，存入session展示在访客页面
 */
@Data
public class BloggerTo {
    /**
     * 头像
     */
    private String avatar;
    /**
     * 博客名
     */
    private String blogName;
    /**
     * 博客介绍
     */
    private String blogIntroduce;
    /**
     * weibo
     */
    private String socialWeibo;
    /**
     * 微信
     */
    private String socialWeixin;
    /**
     * github
     */
    private String socialGithub;
}
