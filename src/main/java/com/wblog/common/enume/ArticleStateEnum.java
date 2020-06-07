package com.wblog.common.enume;


import lombok.Getter;

public enum ArticleStateEnum {
    PUBLIC(1, "公开"),
    SECRET(2, "保密"),
    DRAFT(3, "草稿"),
    DELETE(4, "已删除");

    @Getter
    private Integer code;
    @Getter
    private String msg;

    ArticleStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
