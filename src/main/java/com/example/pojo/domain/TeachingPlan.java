package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * 功能描述：教学计划实体类
 * 该类用于表示教师的教学计划信息，包括教师、课程、班级和教学计划进度等相关信息。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "teaching_plan")
@org.hibernate.annotations.Table(appliesTo = "teaching_plan", comment = "教学计划表")
public class TeachingPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 教学计划的唯一标识符
    
    /**
     * 教学计划标题
     * 表示教学计划的名称或标题
     */
    @Column(name = "title", nullable = false)
    private String title; // 教学计划标题
    
    /**
     * 教学计划内容
     * 表示教学计划的详细内容描述
     */
    @Column(name = "content")
    private String content; // 教学计划内容
    
    /**
     * 开始日期
     * 表示教学计划的开始日期
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 开始日期
    
    /**
     * 结束日期
     * 表示教学计划的结束日期
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 结束日期
    
    
    /**
     * 教师信息
     * 通过多对一关系关联到教师实体
     */
    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher; // 教师对象
    /**
     * 课程信息
     * 通过多对一关系关联到课程实体
     */
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course; // 课程对象
    
    /**
     * 班级信息
     * 通过多对一关系关联到班级实体
     */
    @ManyToOne(targetEntity = GradeClass.class)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private GradeClass classInfo; // 班级对象
    
    /**
     * 总课时
     * 表示教学计划的总课时数量
     */
    @Column(name = "total_lessons", nullable = false)
    private Integer totalLessons; // 总课时
}
