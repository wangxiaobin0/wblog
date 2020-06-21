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
     * 标签id
     */
    private Long tagId;
}
