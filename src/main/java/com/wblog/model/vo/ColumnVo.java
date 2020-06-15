package com.wblog.model.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

@Data
public class ColumnVo implements Serializable {

    /**
     * 专栏id
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
    private Long collectNum;
}
