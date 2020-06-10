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
@TableName("blog_column_item")
public class  ColumnItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 专栏中文章id
	 */
	@TableId
	private Long id;
	/**
	 * 专栏id
	 */
	@NonNull
	private Long columnId;
	/**
	 * 专栏名
	 */
	@NonNull
	private String columnName;
	/**
	 * 文章id
	 */
	@NonNull
	private Long articleId;
	/**
	 * 
	 */
	@NonNull
	private String articleTitle;

	/**
	 * 排序参数
	 */
	@NonNull
	private Integer sort;
}
