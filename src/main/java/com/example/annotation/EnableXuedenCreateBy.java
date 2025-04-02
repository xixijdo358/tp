package com.example.annotation;

import com.example.annotation.generation.CreationCreateByGeneration;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记需要自动填充创建者ID的字段或方法。
 * 在插入数据时，系统会自动调用CreationCreateByGeneration类生成创建者ID并赋值。
 */
@ValueGenerationType(
        generatedBy = CreationCreateByGeneration.class // 指定生成器为CreationCreateByGeneration
)
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可用
@Target({ElementType.FIELD, ElementType.METHOD}) // 注解可以应用于字段和方法
public @interface EnableXuedenCreateBy {
}
