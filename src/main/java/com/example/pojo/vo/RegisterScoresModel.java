package com.example.pojo.vo;

import lombok.Data;

/** 
 * 登记班级学科成绩参数
 * 该类用于封装登记班级学科成绩所需的数据，包括班级ID。
 */
@Data
public class RegisterScoresModel {

    /**
     * 班级ID
     */
    private Long gradeClassId;

    /**
     * 课程ID
     */
    private Long courseId;
}
