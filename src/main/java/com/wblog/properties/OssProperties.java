package com.wblog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("blog.oss")
public class OssProperties {

    /**
     * 端点
     */
    private String endpoint;

    /**
     *
     */
    private String accessKeyId;

    /**
     *
     */
    private String accessKeySecret;

    /**
     *
     */
    private String bucket;
}
