package com.wblog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Data
@Component
@ConfigurationProperties(prefix = "blog.thread")
public class BlogThreadProperties {

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 空闲线程存活时间
     */
    private Long keepAliveTime;

    /**
     * keepAliveTime的单位
     */
    private TimeUnit timeUnit;

    /**
     * 工作队列容量
     */
    private Integer queueCapacity;

}
