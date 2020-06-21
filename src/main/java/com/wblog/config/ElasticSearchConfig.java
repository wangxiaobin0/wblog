package com.wblog.config;

import com.wblog.properties.ElasticSearchProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ElasticSearch配置
 */
@Configuration
public class ElasticSearchConfig {

    @Autowired
    ElasticSearchProperties properties;

    @Bean
    @Primary
    RestHighLevelClient elasticClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(properties.getHost(), properties.getPort(), properties.getSchema().getSchema()));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
