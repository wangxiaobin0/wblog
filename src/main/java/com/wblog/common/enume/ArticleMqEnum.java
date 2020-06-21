package com.wblog.common.enume;

import lombok.Getter;

public enum  ArticleMqEnum {
    PUBLIC(1, "添加到ES"),
    DELETE(2, "从ES删除"),
    DRAFT(3, "草稿箱"),
    TRASH(4, "回收站");

    @Getter
    private Integer code;
    @Getter
    private String msg;

    ArticleMqEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
