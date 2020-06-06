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
@TableName("blog_article")
public class ArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文章id
	 */
	@TableId
	private Long id;
	/**
	 * 管理员ID
	 */
	private Long adminId;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章html内容
	 */
	private String html;
	/**
	 * 文章markdown内容
	 */
	private String markdown;
	/**
	 * 状态。1：公开；2：保密；3：草稿箱；4：回收站
	 */
	private String state;
	/**
	 * 是否置顶。0：否；1：是
	 */
	private Boolean top;
	/**
	 * 浏览数
	 */
	private Long viewNum;
	/**
	 * 点赞数
	 */
	private Long thumbUp;
	/**
	 * 收藏数
	 */
	private Long collectNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date baUpdateTime;
	/**
	 * 
	 */
	private Integer baSort;

}
