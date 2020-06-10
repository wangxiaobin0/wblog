package com.wblog.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wblog.properties.OssProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OssConfig {

    @Autowired
    OssProperties ossProperties;

    @Bean
    public OSS ossClient() {
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
        return ossClient;
    }
}
