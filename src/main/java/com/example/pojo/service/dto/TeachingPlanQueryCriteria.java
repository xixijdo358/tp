package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;
import java.time.LocalDate;

/**
 * 功能描述：教学计划查询条件
 */
@Data
public class TeachingPlanQueryCriteria {

    /**
     * 根据标题、内容模糊查询
     */
    @EnableXuedenQuery(blurry = "title,content")
    private String searchValue;
    
    /**
     * 根据教师ID查询
     */
    @EnableXuedenQuery(joinName="teacher", propName = "id", type = EnableXuedenQuery.Type.EQUAL)
    private Long teacherId;
    
    /**
     * 根据课程ID查询
     */
    @EnableXuedenQuery(joinName="course", propName = "id", type = EnableXuedenQuery.Type.EQUAL)
    private Long courseId;
    
    /**
     * 根据班级ID查询
     */
    @EnableXuedenQuery(joinName="classInfo", propName = "id", type = EnableXuedenQuery.Type.EQUAL)
    private Long classId;
    
    /**
     * 开始日期-开始
     */
    @EnableXuedenQuery(propName = "startDate", type = EnableXuedenQuery.Type.GREATER_THAN)
    private LocalDate startDateStart;
    
    /**
     * 开始日期-结束
     */
    @EnableXuedenQuery(propName = "startDate", type = EnableXuedenQuery.Type.LESS_THAN)
    private LocalDate startDateEnd;
    
    /**
     * 结束日期-开始
     */
    @EnableXuedenQuery(propName = "endDate", type = EnableXuedenQuery.Type.GREATER_THAN)
    private LocalDate endDateStart;
    
    /**
     * 结束日期-结束
     */
    @EnableXuedenQuery(propName = "endDate", type = EnableXuedenQuery.Type.LESS_THAN)
    private LocalDate endDateEnd;
}