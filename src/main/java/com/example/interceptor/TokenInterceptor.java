package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Token拦截器
 * 用于从请求头中获取Token并存储到请求属性中
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取Token
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            // 将Token存储到ServletContext中，以便在其他地方使用
            request.getServletContext().setAttribute("token", token);
        }
        // 继续处理请求
        return true;
    }
}