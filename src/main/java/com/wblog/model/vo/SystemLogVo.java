package com.wblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemLogVo implements Serializable {

    private Long id;

    private String business;

    private Date time;
}
