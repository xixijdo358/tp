package com.example.annotation.generation;

import com.example.annotation.EnableXuedenCreateBy;

import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**功能描述：创建者ID生成器
 * 该类实现了AnnotationValueGeneration接口，用于生成创建者ID。
 * 在插入数据时，自动生成创建者的ID并赋值给相应的字段。
 */
public class CreationCreateByGeneration implements AnnotationValueGeneration<EnableXuedenCreateBy> {
    private ValueGenerator<?> generator; // 存储生成器

    public CreationCreateByGeneration() {
    }

    @Override
    public void initialize(EnableXuedenCreateBy enableXuedenUpdateBy, Class<?> propertyType) {
        // 根据属性类型获取相应的值生成器
        this.generator = CreateByGenerators.get(propertyType);
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        // 指定生成时机为插入时
        return GenerationTiming.INSERT;
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
