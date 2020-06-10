package com.wblog.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 博客展示Vo
 */
@Data
public class ArticleShowVo {
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private List<String> tag;
    /**
     * html
     */
    private String html;
    /**
     * markdown
     */
    private String markdown;

    /**
     * 状态。1：公开；2：保密；3：草稿箱；4：回收站
     */
    private String state;

    /**
     * 是否置顶。0：否；1：是
     */
    private Boolean top;

    /**
     * 浏览数
     */
    private Long viewNum;
    /**
     * 点赞数
     */
    private Long thumbUp;
    /**
     * 收藏数
     */
    private Long collectNum;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     *
     */
    private Integer sort;
}
