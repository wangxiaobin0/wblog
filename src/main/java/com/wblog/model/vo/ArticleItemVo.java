package com.wblog.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 访客显示的博客详情
 */
@Data
public class ArticleItemVo {

    /**
     * 文章id
     */
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章html内容
     */
    private String html;
    /**
     * 浏览数
     */
    private String viewNum;
    /**
     * 点赞数
     */
    private String thumbUp;
    /**
     * 收藏数
     */
    private String collectNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 标签
     */
    private List<TagVo> tags;
}
