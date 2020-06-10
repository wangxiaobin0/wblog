package com.wblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ColumnItemVo implements Serializable {

    /**
     * ColumnItemId
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 收藏人数
     */
    private Integer collectNum;
}
