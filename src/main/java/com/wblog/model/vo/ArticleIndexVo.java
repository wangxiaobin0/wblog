package com.wblog.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
     * 更新时间
     */
    private Date updateTime;

    private List<TagVo> tags;
}
