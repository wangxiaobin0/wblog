package com.wblog.common.constant;

/**
 * 访客相关的常量
 */
public class UserConstant {
    /**
     * 访客浏览
     * list结构
     *  key:    user:view:article:user-key
     *  value:  articleId
     */
    public static final String USER_VIEW_ARTICLE = "user:view:article:";

    /**
     * 访客收藏
     * list结构
     *  key:    user:collect:article:
     *  value:  articleId
     */
    public static final String USER_COLLECT_ARTICLE = "user:collect:article:";


    /**
     * 访客点赞
     * list结构
     *  key:    user:thumbUp:article:
     *  value:  articleId
     */
    public static final String USER_THUMB_UP_ARTICLE = "user:thumbUp:article:";

    /**
     * 访客专栏订阅
     *
     */
    public static final String USER_COLUMN_SUBSCRIBE = "user:subscribe:column:";
}

