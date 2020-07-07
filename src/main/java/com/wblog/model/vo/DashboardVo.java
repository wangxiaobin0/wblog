package com.wblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DashboardVo implements Serializable {
    private String viewNum;
    private Integer articleNum;
    private Integer columnNum;
}
