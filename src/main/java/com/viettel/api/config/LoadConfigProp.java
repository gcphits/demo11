package com.viettel.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config-prop", ignoreUnknownFields = true)
public class LoadConfigProp {
    private Long limitTime;
    private Long requestPerTime;
    private Long tokensToConsume;
    private String keyHits;
    private String keySource;
}
