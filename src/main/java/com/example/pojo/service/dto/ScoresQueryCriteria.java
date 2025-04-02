package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：成绩查询功能
 */
@Data
public class ScoresQueryCriteria {

    /**
     * 根据班级ID查询
     */
    @EnableXuedenQuery(joinName = "gradeClass",propName = "id",type = EnableXuedenQuery.Type.EQUAL)
    private Long gradeClassId;

    /**
     * 学号
     * 左关联查询，left Join ， joinName为关联实体名称 , propName为关联实体 字段
     * type = EnableXuedenQuery.Type.INNER_LIKE 表示中模糊查询
     */
    @EnableXuedenQuery(joinName = "student",propName = "stuno",type = EnableXuedenQuery.Type.INNER_LIKE)
    private String stuno;

    /**
     * 根据学生姓名模糊查询
     */
    @EnableXuedenQuery(joinName = "student",propName = "name",type = EnableXuedenQuery.Type.INNER_LIKE)
    private String name;

    /**
     * 根据课程名称模糊查询
     */
    @EnableXuedenQuery(joinName = "course",propName = "coursename",type = EnableXuedenQuery.Type.INNER_LIKE)
    private String coursename;

    /**
     * 根据课程ID查询
     */
    @EnableXuedenQuery(joinName = "course",propName = "id",type = EnableXuedenQuery.Type.EQUAL)
    private Long courseId;

}
