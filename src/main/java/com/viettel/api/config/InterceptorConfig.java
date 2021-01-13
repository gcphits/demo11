package com.viettel.api.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.viettel.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    RateLimitingInterceptor rateLimitingInterceptor;

    @Qualifier("hazelcastInstance")
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitingInterceptor)
                .addPathPatterns(Constants.InterceptorPathPattern.CONVERT_JSON_ALLOW_PATH);
    }

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName(Constants.HAZELCAST_INSTANCE_NAME);
        return config;
    }
}