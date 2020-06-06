package com.wblog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Data
@TableName("blog_admin_profile")
public class AdminProfileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long adminId;
	/**
	 * 
	 */
	private String avatar;
	/**
	 * 
	 */
	private String blogName;
	/**
	 * 
	 */
	private String blogIntroduce;
	/**
	 * 
	 */
	private String socialWeibo;
	/**
	 * 
	 */
	private String socialWeixin;
	/**
	 * 
	 */
	private String socialGithub;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
