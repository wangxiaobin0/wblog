package com.wblog.model.to;

import com.wblog.model.vo.TagVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章Es
 */
@Data
public class ArticleEsTo implements Serializable {

    /**
     * 文章id
     */
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 标签
     */
    private List<TagVo> tags;
}
