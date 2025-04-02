package com.example.pojo.service;

import com.example.pojo.domain.SysRole;
import com.example.pojo.service.dto.RoleQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/** 
 * 功能描述：系统角色接口
 * 该接口定义了与系统角色相关的业务逻辑，包括获取角色列表、添加角色、获取角色详情、更新角色信息、删除角色以及获取所有角色等功能。
 */
public interface IRoleService {
    /**
     * 获取角色列表数据
     * @param queryCriteria 查询条件，用于过滤角色
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的角色列表
     */
    Object getList(RoleQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加角色信息
     * @param sysRole 角色对象，包含角色的详细信息
     * @return 返回添加是否成功
     */
    boolean addRole(SysRole sysRole);

    /**
     * 根据ID获取角色详情信息
     * @param id 角色ID
     * @return 返回对应的角色对象
     */
    SysRole getById(Long id);

    /**
     * 更新角色信息
     * @param sysRole 角色对象，包含更新后的角色信息
     */
    void editRole(SysRole sysRole);

    /**
     * 删除角色信息
     * @param id 角色ID
     */
    void deleteById(Long id);

    /**
     * 获取所有角色
     * @return 返回所有角色的列表
     */
    List<SysRole> queryAll();
}
