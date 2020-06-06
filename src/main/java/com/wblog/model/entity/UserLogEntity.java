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
@TableName("blog_user_log")
public class UserLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 访问地址
	 */
	private String url;
	/**
	 * 参数
	 */
	private String parameter;
	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 访问结果。00：失败；10：成功
	 */
	private String state;
	/**
	 * 状态消息
	 */
	private String stateMessage;

}
