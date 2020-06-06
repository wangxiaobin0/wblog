package com.wblog.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 专栏/标签关联表
 * 
 * @author wangxb
 * @email 
 */
@Data
@TableName("blog_column_tag")
public class ColumnTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 专栏id
	 */
	private Long columnId;
	/**
	 * 标签id
	 */
	private Long tagId;

}
