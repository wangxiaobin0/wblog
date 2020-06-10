package com.wblog.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

/**
 * 专栏详情页vo
 */
@Data
public class ColumnDetailVo {

    /**
     * 专栏Id
     */
    private Long id;
    /**
     * 专栏名称
     */
    private String name;
    /**
     * 专栏简介
     */
    private String summary;
    /**
     * 专栏封面
     */
    private String image;
    /**
     * 专栏收藏人数
     */
    private Integer collectNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 上次更新时间
     */
    private Date updateTime;

    /**
     * 专栏中的文章
     */
    List<ColumnItemVo> itemVos;
}
