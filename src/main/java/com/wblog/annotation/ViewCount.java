package com.wblog.annotation;

import java.lang.annotation.*;

/**
 * 更新全站访问次数
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewCount {
}
