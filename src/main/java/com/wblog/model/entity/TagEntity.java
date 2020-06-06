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
@TableName("blog_tag")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@TableId
	private Long id;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 文章数量
	 */
	private Integer articleNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 1：开放；2：保密；3：删除
	 */
	private String state;

}
