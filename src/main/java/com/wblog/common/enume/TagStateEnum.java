package com.wblog.common.enume;

import lombok.Getter;

public enum TagStateEnum {
    PUBLIC(1, "公开"),
    DRAFT(2, "已删除");

    @Getter
    private Integer code;
    @Getter
    private String msg;

    TagStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
