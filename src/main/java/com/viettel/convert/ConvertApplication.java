package com.viettel.convert;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.viettel.convert.filter.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ConvertApplication implements WebMvcConfigurer {

    @Qualifier("hazelcastInstance")
    @Autowired
    private HazelcastInstance hazelcastInstance;

    public static void main(String[] args) {
        SpringApplication.run(ConvertApplication.class, args);
    }

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("application-hazel-instance");
        return config;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestFilter())
                .addPathPatterns("/convert/**");

    }

}
