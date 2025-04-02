package com.example.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 
 * 功能描述：配置跨域
 * 该类用于配置跨域请求的相关设置，确保前端和后端能够正常通信。
 */
@Configuration
public class ConfigurerAdapter implements WebMvcConfigurer {
    @Value("${user.icon}")
    private String userIcon; // 从配置文件中读取用户图标的路径

    /**
     * 跨域配置
     * 该方法用于配置跨域请求的相关参数，包括允许的请求头、请求方法、有效时间等。
     */
    @Override
    @Order(1) // 设置优先级为1
    public void addCorsMappings(CorsRegistry registry) {
        // 对所有请求路径进行跨域处理
        registry.addMapping("/**")
                // 允许的请求头，默认允许所有的请求头
                .allowedHeaders("*")
                // 允许的方法，默认允许GET、POST、HEAD
                .allowedMethods("*")
                // 探测请求有效时间，单位为秒
                .maxAge(1800)
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 支持的域，允许所有域名
                .allowedOriginPatterns("*");
    }

    /**
     * 静态资源文件路径映射
     * 该方法用于配置静态资源的访问路径，将用户图标的路径映射到指定的URL。
     * @param registry 资源处理注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("userIcon:" + userIcon); // 输出用户图标路径
        String pathUtl = "file:" + userIcon.replace("\\", "/"); // 替换路径中的反斜杠为正斜杠
        // 映射静态资源路径
        registry.addResourceHandler("/uploadFile/**").addResourceLocations(pathUtl).setCachePeriod(0);
    }
}
