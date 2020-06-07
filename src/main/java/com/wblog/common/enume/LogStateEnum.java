package com.wblog.common.enume;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import lombok.Getter;

public enum LogStateEnum {
    SUCCESS("10", "成功"),
    FAIL("00", "失败");

    @Getter
    private String code;

    @Getter
    private String message;

    LogStateEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
