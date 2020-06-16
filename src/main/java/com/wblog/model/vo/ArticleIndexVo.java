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
    private Long viewCount;

    /**
     * 收藏数
     */
    private Long collectNum;

    /**
     * 点赞数
     */
    private Long thumbUpCount;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 是否置顶
     */
    private Boolean top;
}
