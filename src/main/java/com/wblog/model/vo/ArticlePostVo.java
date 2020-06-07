package com.wblog.model.vo;

import lombok.Data;

/**
 * 新增博客Vo
 */
@Data
public class ArticlePostVo {

    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private String tag;
    /**
     * html
     */
    private String html;
    /**
     * markdown
     */
    private String markdown;

    /**
     * 状态
     */
    private String state;
}
