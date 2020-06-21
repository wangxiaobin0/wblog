package com.wblog.annotation;

import java.lang.annotation.*;

/**
 * 统一处理rabbitmq消息消费结果
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rabbit {
}
