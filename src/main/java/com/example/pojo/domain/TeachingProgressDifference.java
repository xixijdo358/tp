package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 功能描述：教学进度差异实体类
 * 该类用于表示教学进度与预期进度的差异情况，包括差异值、差异百分比、预警级别等信息。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "teaching_progress_difference")
@org.hibernate.annotations.Table(appliesTo = "teaching_progress_difference", comment = "教学进度差异记录表")
public class TeachingProgressDifference extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 差异记录的唯一标识符
    
    /**
     * 关联的教学计划
     * 通过多对一关系关联到教学计划实体
     */
    @ManyToOne(targetEntity = TeachingPlan.class)
    @JoinColumn(name = "teaching_plan_id", referencedColumnName = "id", nullable = false)
    private TeachingPlan teachingPlan; // 教学计划对象
    
    /**
     * 关联的教学进度
     * 通过多对一关系关联到教学进度实体
     */
    @ManyToOne(targetEntity = TeachingProgress.class)
    @JoinColumn(name = "teaching_progress_id", referencedColumnName = "id", nullable = false)
    private TeachingProgress teachingProgress; // 教学进度对象
    
    /**
     * 实际进度
     * 表示实际完成的进度百分比
     */
    @Column(name = "actual_progress", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualProgress; // 实际进度
    
    /**
     * 预期进度
     * 表示根据计划应该达到的进度百分比
     */
    @Column(name = "expected_progress", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedProgress; // 预期进度
    
    /**
     * 差异值
     * 表示实际进度与预期进度的差值（课时数）
     */
    @Column(name = "difference", nullable = false)
    private Integer difference; // 差异值
    
    /**
     * 差异百分比
     * 表示实际进度与预期进度的差异百分比
     */
    @Column(name = "difference_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal differencePercentage; // 差异百分比
    
    /**
     * 预警标志
     * 表示是否触发预警（0-无预警，1-有预警）
     */
    @Column(name = "warning", nullable = false)
    private Boolean warning; // 预警标志
    
    /**
     * 预警级别
     * 表示预警的级别（0-无预警，1-轻微，2-严重）
     */
    @Column(name = "warning_level", nullable = false)
    private Integer warningLevel; // 预警级别
    
    /**
     * 预警消息
     * 表示预警的具体描述信息
     */
    @Column(name = "warning_message")
    private String warningMessage; // 预警消息
    
    /**
     * 状态
     * 表示差异记录的状态（1-正常，0-已处理）
     */
    @Column(name = "status", nullable = false)
    private Integer status; // 状态
    
    /**
     * 教学计划ID（非持久化字段，用于前端数据传输）
     */
    @Transient
    private Long teachingPlanId;
    
    /**
     * 教学进度ID（非持久化字段，用于前端数据传输）
     */
    @Transient
    private Long teachingProgressId;
    
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
    
    /**
     * 获取教学进度ID
     */
    public Long getTeachingProgressId() {
        return teachingProgress != null ? teachingProgress.getId() : teachingProgressId;
    }
    
    /**
     * 设置教学进度ID
     */
    public void setTeachingProgressId(Long teachingProgressId) {
        this.teachingProgressId = teachingProgressId;
    }
} 