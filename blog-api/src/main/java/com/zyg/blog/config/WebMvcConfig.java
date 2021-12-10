package com.zyg.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    // 重写方法的注释
    public void addCorsMappings (CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
