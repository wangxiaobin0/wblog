package com.wblog.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagStatisticsVo implements Serializable {
    /**
     * tag名称
     */
    private String name;
    /**
     * 文章数量
     */
    private Integer value;
}
