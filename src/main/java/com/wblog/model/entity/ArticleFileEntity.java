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
@TableName("blog_artile_file")
public class ArticleFileEntity implements Serializable {
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
	private Long articleId;
	/**
	 * 
	 */
	private String filePath;
	/**
	 * 
	 */
	private Date uploadTime;

}
