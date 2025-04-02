package com.example.pojo.vo;

import lombok.Data;

/**
 * 修改密码模型
 * 该类用于封装修改密码所需的数据，包括旧密码和新密码。
 */
@Data
public class ModifyPwdModel {

    /**
     * 旧密码
     */
    private String usedPass;

    /**
     * 新密码
     */
    private String newPass;

    /**
     * 登录用户ID
     */
    private Long userId;
}
