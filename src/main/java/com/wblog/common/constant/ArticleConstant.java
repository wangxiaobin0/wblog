package com.wblog.common.constant;

/**
 * 文章相关常量
 */
public class ArticleConstant {

    /**
     * 浏览过文章的访客列表
     * list结构
     *  key: article:view:articleId
     *  value: user-key
     *  rpush
     */
    public static final String ARTICLE_VIEW = "article:view:";

    /**
     * 文章浏览次数
     * string
     */
    public static final String ARTICLE_VIEW_COUNT = "article:view:count:";

    /**
     * 收藏过文章的访客列表
     * list结构
     *  key: article:collect:articleId
     *  value: user-key
     *  rpush
     */
    public static final String ARTICLE_COLLECT = "article:collect:";
    /**
     * 文章浏览次数
     * string
     */
    public static final String ARTICLE_COLLECT_COUNT = "article:collect:count:";

    /**
     * 点赞过文章的访客列表
     * list结构
     *  key: article:collect:articleId
     *  value: user-key
     *  rpush
     */
    public static final String ARTICLE_THUMB_UP = "article:thumbUp:";

    /**
     * 文章点赞次数
     * string
     */
    public static final String ARTICLE_THUMB_UP_COUNT = "article:thumbUp:count:";
}
