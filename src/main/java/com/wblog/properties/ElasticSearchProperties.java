package com.wblog.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "blog.elastic")
public class ElasticSearchProperties {

    private String host;

    private Integer port;

    private SchemaEnum schema;

    public enum SchemaEnum {
        http("http");

        @Getter
        private String schema;
        SchemaEnum(String schema) {
            this.schema = schema;
        }
    }
}
