package com.example.annotation.generation;

import com.example.utils.HutoolJWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.tuple.ValueGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 该类用于生成更新者ID的值生成器
 * 通过解析请求中的token来获取更新者的ID。
 */
final class UpdateByGenerators {
    // 存储不同类型的值生成器
    private static final Map<Class<?>, ValueGenerator<?>> generators = new HashMap<>();

    // 构造函数
    public UpdateByGenerators() {
    }

    // 根据类型获取对应的值生成器
    public static ValueGenerator<?> get(Class<?> type) {
        ValueGenerator<?> valueGeneratorSupplier = (ValueGenerator<?>) generators.get(type);
        // 如果没有找到对应的生成器，返回null
        if (Objects.isNull(valueGeneratorSupplier)) {
            return null;
        } else {
            return valueGeneratorSupplier; // 返回找到的生成器
        }
    }

    // 静态代码块，初始化生成器
    static {
        // 为Long类型注册一个值生成器
        generators.put(java.lang.Long.class, (session, owner) -> {
            try {
                // 获取当前请求
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    // 从请求头中获取Token
                    String token = request.getHeader("Authorization");
                    
                    // 如果请求头中没有Token，尝试从ServletContext获取
                    if (token == null || token.isEmpty()) {
                        token = (String) request.getServletContext().getAttribute("token");
                    }
                    
                    // 验证Token格式并解析
                    if (token != null && !token.isEmpty() && token.contains(".")) {
                        try {
                            return HutoolJWTUtil.parseToken(token);
                        } catch (Exception e) {
                            // Token解析失败，记录异常但不中断流程
                            System.out.println("Token解析失败: " + e.getMessage());
                        }
                    }
                }
                // 如果无法获取有效Token，返回默认值
                return 2L; // 默认系统操作员ID
            } catch (Exception e) {
                // 记录异常但不中断流程
                e.printStackTrace();
                return 2L; // 出现异常时返回默认值
            }
        });
    }
}
