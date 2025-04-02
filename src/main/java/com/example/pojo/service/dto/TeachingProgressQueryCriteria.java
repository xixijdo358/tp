package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;
import java.time.LocalDate;

/**
 * 功能描述：教学进度查询条件
 */
@Data
public class TeachingProgressQueryCriteria {

    /**
     * 根据完成内容模糊查询
     */
    @EnableXuedenQuery(blurry = "completedContent")
    private String keyword;
    
    /**
     * 根据教学计划ID查询
     */
    @EnableXuedenQuery(joinName="teachingPlan", propName = "id", type = EnableXuedenQuery.Type.EQUAL)
    private Long teachingPlanId;
    
    /**
     * 根据教师名称模糊查询
     */
    @EnableXuedenQuery(joinName="teachingPlan.teacher", propName = "name", type = EnableXuedenQuery.Type.INNER_LIKE)
    private String teacherName;
    
    /**
     * 根据课程名称模糊查询
     */
    @EnableXuedenQuery(joinName="teachingPlan.course", propName = "coursename", type = EnableXuedenQuery.Type.INNER_LIKE)
    private String courseName;
    
    /**
     * 根据班级名称模糊查询
     */
    @EnableXuedenQuery(joinName="teachingPlan.classInfo", propName = "name", type = EnableXuedenQuery.Type.INNER_LIKE)
    private String className;
    
    /**
     * 记录日期-开始
     */
    @EnableXuedenQuery(propName = "recordDate", type = EnableXuedenQuery.Type.GREATER_THAN)
    private LocalDate startDate;
    
    /**
     * 记录日期-结束
     */
    @EnableXuedenQuery(propName = "recordDate", type = EnableXuedenQuery.Type.LESS_THAN)
    private LocalDate endDate;
} 