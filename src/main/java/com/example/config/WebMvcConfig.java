package com.example.config;

import com.example.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    public WebMvcConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加Token拦截器，拦截所有请求
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                // 排除不需要验证Token的路径
                .excludePathPatterns("/login", "/register", "/error", "/swagger-ui/**", "/v3/api-docs/**");
    }
}