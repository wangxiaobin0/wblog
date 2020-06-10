package com.wblog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NonNull;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Data
@TableName("blog_column")
public class ColumnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 专栏id
	 */
	@TableId
	private Long id;
	/**
	 * 管理员id
	 */
	private Long adminId;
	/**
	 * 专栏名称
	 */
	@NonNull
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
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 上次更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 1: 开放; 2:删除
	 */
	private String state;
	/**
	 * 排序
	 */
	private Integer sort;

}
