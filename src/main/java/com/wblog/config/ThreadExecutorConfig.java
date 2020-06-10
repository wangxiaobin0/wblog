package com.wblog.config;


import com.wblog.properties.BlogThreadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadExecutorConfig {

    @Autowired
    BlogThreadProperties blogThreadProperties;


    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                blogThreadProperties.getCorePoolSize(),
                blogThreadProperties.getMaxPoolSize(),
                blogThreadProperties.getKeepAliveTime(),
                blogThreadProperties.getTimeUnit(),
                new LinkedBlockingDeque<>(blogThreadProperties.getQueueCapacity())
        );
    }
}
