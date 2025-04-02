package com.example.pojo.service;

import com.example.pojo.domain.SysUser;
import com.example.pojo.service.dto.UserQueryCriteria;
import com.example.pojo.vo.ModifyPwdModel;
import org.springframework.data.domain.Pageable;

/** 
 * 功能描述：系统用户业务接口
 * 该接口定义了与系统用户相关的业务逻辑，包括用户登录、获取用户列表、添加用户、获取用户详情、更新用户信息、删除用户以及更新用户密码等功能。
 */
public interface ISysUserService {

    /**
     * 登录
     * @param Users 用户对象，包含登录所需的用户信息
     * @return 返回登录成功的用户对象
     */
    SysUser login(SysUser sysUser);

    /**
     * 获取用户列表数据
     * @param queryCriteria 查询条件，用于过滤用户
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的用户列表
     */
    Object getList(UserQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加用户信息
     * @param Users 用户对象，包含用户的详细信息
     * @return 返回添加是否成功
     */
    boolean addUser(SysUser sysUser);

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 返回对应的用户对象
     */
    SysUser getById(Long id);

    /**
     * 更新用户信息
     * @param Users 用户对象，包含更新后的用户信息
     */
    void editUser(SysUser sysUser);

    /**
     * 根据ID删除用户信息
     * @param id 用户ID
     */
    void deleteById(Long id);

    /**
     * 更新个人密码
     * @param modifyPwdModel 包含用户ID和新密码的模型
     * @return 返回更新是否成功
     */
    boolean updatePwd(ModifyPwdModel modifyPwdModel);
}
