package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：系统用户查询条件参数
 */
@Data
public class UserQueryCriteria {

    /**
     * 根据用户名、真实姓名、邮箱模糊查询
     */
    @EnableXuedenQuery(blurry = "username,realname,email")
    private String searchValue;


    /**
     * 根据性别模糊查询
     */
    @EnableXuedenQuery
    private String sex;


    /**
     * 状态
     */
    @EnableXuedenQuery()
    private String status;
}
