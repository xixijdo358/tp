package com.example.pojo.service.dto;

import com.example.annotation.EnableXuedenQuery;
import lombok.Data;

/**功能描述：系统角色查询条件
 */
@Data
public class RoleQueryCriteria {
    /**
     * 根据角色名称、角色编号模糊查询
     */
    @EnableXuedenQuery(blurry = "name,code")
    private String searchValue;
}
