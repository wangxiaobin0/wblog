package com.wblog.model.vo;

import com.wblog.model.entity.TagEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class ArticleListVo {

    /**
     * 标题
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private List<TagEntity> tags;
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
     * 修改时间
     */
    private Date updateTime;

    /**
     * 剩余时间
     */
    private Integer remainTime;
}
