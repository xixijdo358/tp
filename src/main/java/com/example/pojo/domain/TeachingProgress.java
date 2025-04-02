package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 功能描述：教学进度实体类
 * 该类用于表示教学进度记录，包括关联的教学计划、完成内容、进度百分比、预期进度、完成课时和记录日期等信息。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "teaching_progress")
@org.hibernate.annotations.Table(appliesTo = "teaching_progress", comment = "教学进度表")
public class TeachingProgress extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 教学进度的唯一标识符
    
    /**
     * 关联的教学计划
     * 通过多对一关系关联到教学计划实体
     */
    @ManyToOne(targetEntity = TeachingPlan.class)
    @JoinColumn(name = "teaching_plan_id", referencedColumnName = "id", nullable = false)
    private TeachingPlan teachingPlan; // 教学计划对象
    
    /**
     * 完成内容
     * 表示已完成的教学内容描述
     */
    @Column(name = "completed_content", columnDefinition = "text")
    private String completedContent; // 完成内容
    
    /**
     * 进度百分比
     * 表示当前实际完成的进度百分比
     */
    @Column(name = "progress_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal progressPercentage; // 进度百分比
    
    /**
     * 预期进度
     * 表示根据计划日期计算出的理论上应该达到的进度百分比
     */
    @Column(name = "expected_progress", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedProgress; // 预期进度
    
    /**
     * 完成课时
     * 表示已完成的课时数量
     */
    @Column(name = "lessons_completed", nullable = false)
    private Integer lessonsCompleted; // 完成课时
    
    /**
     * 记录日期
     * 表示进度记录的日期
     */
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate; // 记录日期
    
    /**
     * 教学计划ID（非持久化字段，用于前端数据传输）
     */
    @Transient
    private Long teachingPlanId;
    
    /**
     * 获取教学计划ID
     */
    public Long getTeachingPlanId() {
        return teachingPlan != null ? teachingPlan.getId() : teachingPlanId;
    }
    
    /**
     * 设置教学计划ID
     */
    public void setTeachingPlanId(Long teachingPlanId) {
        this.teachingPlanId = teachingPlanId;
    }
} 