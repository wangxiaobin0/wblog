package com.wblog.annotation;

import java.lang.annotation.*;

/**
 * 更新文章访问次数
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ArticleViewCount {
    String articleId();
}
