package com.wblog.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "密码不能为空")
    private String password;
}
