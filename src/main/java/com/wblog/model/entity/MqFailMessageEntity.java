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
@TableName("blog_mq_fail_message")
public class MqFailMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 博主id
	 */
	private Long adminId;
	/**
	 * 消息体
	 */
	private String message;
	/**
	 * 状态。1：发布失败；2：投递失败
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
