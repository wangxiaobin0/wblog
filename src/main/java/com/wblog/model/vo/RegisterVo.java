package com.wblog.model.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 注册信息
 */
@Data
public class RegisterVo implements Serializable {

    @NotBlank(message = "博客名不能为空")
    private String blogName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
