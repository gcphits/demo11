package com.viettel.api;

import com.viettel.api.config.LoadConfigProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties({LoadConfigProp.class})
public class APIServiceApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(APIServiceApplication.class, args);
    }
}