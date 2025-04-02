package com.example.annotation;

import com.example.annotation.generation.CreationUpdateByGeneration;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记需要自动填充更新者ID的字段或方法。
 * 在更新数据时，系统会自动调用CreationUpdateByGeneration类生成更新者ID并赋值。
 */
@ValueGenerationType(
        generatedBy = CreationUpdateByGeneration.class // 指定生成器为CreationUpdateByGeneration
)
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可用
@Target({ElementType.FIELD, ElementType.METHOD}) // 注解可以应用于字段和方法
public @interface EnableXuedenUpdateBy {
}
