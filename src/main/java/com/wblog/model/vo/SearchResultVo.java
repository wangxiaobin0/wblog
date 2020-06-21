package com.wblog.model.vo;

import com.wblog.model.to.ArticleEsTo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchResultVo implements Serializable {

    /**
     * 搜索条件
     */
    private SearchParamVo searchParam;
    /**
     * 花费时间
     */
    private String took;

    /**
     * 命中
     */
    private Long totalHits;

    /**
     * 文章列表
     */
    private List<ArticleEsTo> articles;

    /**
     * 标签列表
     */
    private List<TagSearchVo> tags;

    @Data
    @AllArgsConstructor
    public static class TagSearchVo {
        private Long id;
        private String name;
        private Long docCount;
    }
}
