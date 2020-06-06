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
@TableName("blog_user_message")
public class UserMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 消息类型。00：系统消息；01：评论消息；02：订阅消息
	 */
	private String type;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 是否已读。0：否；1：是
	 */
	private Boolean read;

}
