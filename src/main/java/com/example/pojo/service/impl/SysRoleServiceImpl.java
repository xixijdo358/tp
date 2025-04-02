package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.SysRole;
import com.example.pojo.repository.SysRoleRepository;
import com.example.pojo.service.IRoleService;
import com.example.pojo.service.dto.RoleQueryCriteria;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 * 功能描述：系统角色接口实现类
 * 该类实现了IRoleService接口，提供了与系统角色相关的业务逻辑。
 * 包含获取角色列表、添加角色、根据ID获取角色、更新角色、删除角色和获取所有角色等功能。
 */
@Service
@Transactional(readOnly = true)
public class SysRoleServiceImpl implements IRoleService {

    private final SysRoleRepository sysRoleRepository; // 系统角色数据访问接口

    // 构造函数，注入系统角色的仓库
    public SysRoleServiceImpl(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    /**
     * 获取角色列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回角色列表的分页数据
     */
    @Override
    public Object getList(RoleQueryCriteria queryCriteria, Pageable pageable) {
        // 根据查询条件和分页信息获取角色数据
        Page<SysRole> page = sysRoleRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page); // 转换为分页格式
    }

    /**
     * 新增角色信息
     * @param sysRole 角色对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(SysRole sysRole) {
        // 保存角色信息到数据库
        SysRole dbSysRole = sysRoleRepository.save(sysRole);
        // 检查角色ID是否不为空以确认添加成功
        return dbSysRole.getId() != null;
    }

    /**
     * 根据ID获取角色信息
     * @param id 角色ID
     * @return 返回角色对象
     */
    @Override
    public SysRole getById(Long id) {
        // 根据ID查找角色，如果未找到则返回一个新的角色对象
        return sysRoleRepository.findById(id).orElseGet(SysRole::new);
    }

    /**
     * 更新角色信息
     * @param sysRole 角色对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(SysRole sysRole) {
        // 获取角色的当前状态
        SysRole dbSysRole = sysRoleRepository.getReferenceById(sysRole.getId());
        // 复制属性，忽略空值和错误
        BeanUtil.copyProperties(sysRole, dbSysRole, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 保存更新后的角色信息
        sysRoleRepository.save(dbSysRole);
    }

    /**
     * 根据ID删除角色信息
     * @param id 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 根据ID删除角色信息
        sysRoleRepository.deleteById(id);
    }

    /**
     * 获取所有角色
     * @return 返回所有角色的列表
     */
    @Override
    public List<SysRole> queryAll() {
        // 获取所有角色信息
        return sysRoleRepository.findAll();
    }
}
