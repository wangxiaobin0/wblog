package com.wblog.model.to;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博主登录信息
 */
@Data
public class AdminTo implements Serializable {


    /**
     * adminId
     */
    private Long adminId;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 博客名
     */
    private String blogName;
    /**
     * 博客介绍
     */
    private String blogIntroduce;
    /**
     * weibo
     */
    private String socialWeibo;
    /**
     * 微信
     */
    private String socialWeixin;
    /**
     * github
     */
    private String socialGithub;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 上次登录时间
     */
    private Date updateTime;

}
