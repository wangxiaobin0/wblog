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
@TableName("blog_user_collect")
public class UserCollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 文章标题
	 */
	private String articleTitle;
	/**
	 * 收藏时间
	 */
	private Date createTime;
	/**
	 * 1：收藏：0：删除
	 */
	private String state;

}
