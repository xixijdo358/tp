package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**功能描述：自定义注解，用于查询
 * 该注解用于标记实体类中的字段，以便在查询时使用。
 * 通过该注解，可以指定字段的属性名、查询方式、连接查询的属性名、连接类型以及模糊搜索的字段。
 */
@Target(ElementType.FIELD) // 注解可以应用于字段
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可用
public @interface EnableXuedenQuery {
    // 基本对象的属性名
    String propName() default "";

    // 查询方式
    Type type() default Type.EQUAL; // 默认查询方式为相等

    /**
     * 连接查询的属性名，如User类中的dept
     */
    String joinName() default ""; // 默认连接查询属性名为空

    /**
     * 默认左连接
     */
    Join join() default Join.LEFT; // 默认连接类型为左连接

    /**
     * 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username")
     */
    String blurry() default ""; // 默认模糊搜索字段为空

    enum Type {
        // 相等
        EQUAL,
        // 大于等于
        GREATER_THAN,
        // 小于等于
        LESS_THAN,
        // 中模糊查询
        INNER_LIKE,
        // 左模糊查询
        LEFT_LIKE,
        // 右模糊查询
        RIGHT_LIKE,
        // 小于
        LESS_THAN_NQ,
        // 包含
        IN,
        // 不等于
        NOT_EQUAL,
        // between
        BETWEEN,
        // 不为空
        NOT_NULL
    }

    /**
     * 适用于简单连接查询，复杂的请自定义该注解，或者使用sql查询
     */
    enum Join {
        /** 左右连接 */
        LEFT, RIGHT
    }
}
