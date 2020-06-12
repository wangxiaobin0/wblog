package com.wblog.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 访客首页文章
 */
@Data
public class ArticleIndexVo {

    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     *
     */
    private String abstractHtml;

    /**
     * 浏览数
     */
    private String viewCount;

    /**
     * 收藏数
     */
    private String collectNum;

    /**
     * 点赞数
     */
    private String thumbUpCount;

    /**
     * 更新时间
     */
    private Date updateTime;
}
