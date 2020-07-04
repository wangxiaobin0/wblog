package com.wblog.model.vo;

import lombok.Data;
import lombok.NonNull;

/**
 * 搜索条件vo
 */
@Data
public class SearchParamVo {

    /**
     * 关键字
     */
    @NonNull
    private String key;

    /**
     * 页码
     */
    private Long page = 1l;

    /**
     * 显示数量
     */
    private Long size = 5l;

    /**
     * 标签id
     */
    private Long tagId;
}
