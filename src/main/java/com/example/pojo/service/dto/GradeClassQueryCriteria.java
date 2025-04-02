package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：班级查询条件
 */
@Data
public class GradeClassQueryCriteria {
    /**
     * 根据班级名称、班级编号模糊查询
     */
    @EnableXuedenQuery(blurry = "name,code")
    private String searchValue;
}
