package com.wblog.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Data
@TableName("blog_column_item")
public class ColumnItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 专栏中文章id
	 */
	@TableId
	private Long id;
	/**
	 * 专栏id
	 */
	private Long columnId;
	/**
	 * 专栏名
	 */
	private String columnName;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 
	 */
	private String articleTitle;
	/**
	 * 添加到专栏的时间
	 */
	private Date addTime;

}
