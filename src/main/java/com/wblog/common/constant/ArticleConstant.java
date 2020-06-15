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
     * 文章浏览次数.由于浏览列表是set集合，会去重，不能同意同一个访客的多次访问
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
     * 点赞过文章的访客列表
     * list结构
     *  key: article:collect:articleId
     *  value: user-key
     *  rpush
     */
    public static final String ARTICLE_THUMB_UP = "article:thumbUp:";
}
