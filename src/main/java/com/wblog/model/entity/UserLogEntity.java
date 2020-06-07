package com.wblog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author wangxb
 * @email 
 */
@Data
@TableName("blog_system_log")
public class UserLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * adminId
	 */
	private Long adminId;
	/**
	 * 用户userKey
	 */
	private String userKey;
	/**
	 * 访问地址
	 */
	private String url;
	/**
	 * 类名
	 */
	private String className;

	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 参数
	 */
	private String parameter;

	/**
	 * 交易名
	 */
	private String business;

	/**
	 * 时间
	 */
	@TableField(fill = FieldFill.INSERT)
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
