package com.example.annotation.generation;

import com.example.annotation.EnableXuedenUpdateBy;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * 该类实现了AnnotationValueGeneration接口，用于生成更新者ID。
 * 在数据更新时，自动生成更新者的ID并赋值给相应的字段。
 */
public class CreationUpdateByGeneration implements AnnotationValueGeneration<EnableXuedenUpdateBy> {
    private ValueGenerator<?> generator; // 存储生成器

    public CreationUpdateByGeneration() {
        // 构造函数
    }

    @Override
    public void initialize(EnableXuedenUpdateBy enableXuedenUpdateBy, Class<?> propertyType) {
        // 根据属性类型获取相应的值生成器
        this.generator = UpdateByGenerators.get(propertyType);
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        // 指定生成时机为每次更新
        return GenerationTiming.ALWAYS;
    }

    @Override
    public ValueGenerator<?> getValueGenerator() {
        // 返回当前的值生成器
        return this.generator;
    }

    @Override
    public boolean referenceColumnInSql() {
        // 指示在SQL中不引用列
        return false;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        // 返回数据库生成的引用列值，当前不使用
        return null;
    }
}
