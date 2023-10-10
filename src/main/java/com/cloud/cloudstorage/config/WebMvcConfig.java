package com.cloud.cloudstorage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${origin.allowed}")
    private String originAllowed;

    @Override
    //Разрешаем обрабатывать все запросы, методы и заголовки с фронта (localhost:8080)
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(originAllowed)
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
