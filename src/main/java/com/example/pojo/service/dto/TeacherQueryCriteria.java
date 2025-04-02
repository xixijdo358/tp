package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：教师信息查询条件
 */
@Data
public class TeacherQueryCriteria {
    /**
     * 根据教师姓名、教师工号、手机号、qq模糊查询
     */
    @EnableXuedenQuery(blurry = "name,teachno,phone,qq")
    private String searchValue;
}
