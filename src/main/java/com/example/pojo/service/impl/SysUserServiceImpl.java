package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.SysUser;
import com.example.pojo.repository.SysUserRepository;
import com.example.pojo.service.ISysUserService;
import com.example.pojo.service.dto.UserQueryCriteria;
import com.example.pojo.vo.ModifyPwdModel;
import com.example.utils.Md5Util;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
 * 功能描述：系统用户业务接口实现类
 * 该类实现了ISysUserService接口，提供了与系统用户相关的业务逻辑。
 * 包含登录、获取用户列表、新增用户、根据ID获取用户、更新用户、删除用户和更新个人密码等功能。
 */
@Service
@Transactional(readOnly = true)
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserRepository sysUserRepository;

    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * 登录
     * @param sysUser 用户对象，包含用户名和密码
     * @return 返回数据库中对应的用户对象
     */
    @Override
    public SysUser login(SysUser sysUser) {
        // 根据用户名查找用户
        SysUser dbSysUser = sysUserRepository.findByusername(sysUser.getUsername());
        return dbSysUser; // 返回查找到的用户
    }

    /**
     * 获取用户列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回分页的用户列表
     */
    @Override
    public Object getList(UserQueryCriteria queryCriteria, Pageable pageable) {
        // 根据查询条件和分页信息获取用户数据
        Page<SysUser> page = sysUserRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page); // 转换为分页格式并返回
    }

    /**
     * 新增用户信息
     * @param sysUser 用户对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(SysUser sysUser) {
        // 对用户密码进行MD5加密
        sysUser.setPassword(Md5Util.MD5(sysUser.getPassword()));
        // 保存用户信息到数据库
        SysUser dbSysUser = sysUserRepository.save(sysUser);
        return dbSysUser.getId() != null; // 检查用户ID是否不为空以确认添加成功
    }

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 返回用户对象
     */
    @Override
    public SysUser getById(Long id) {
        // 根据ID查找用户，如果未找到则返回一个新的用户对象
        return sysUserRepository.findById(id).orElseGet(SysUser::new);
    }

    /**
     * 更新用户信息
     * @param sysUser 用户对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUser(SysUser sysUser) {
        // 获取数据库中对应的用户对象
        SysUser dbSysUser = sysUserRepository.getReferenceById(sysUser.getId());
        // 复制用户信息，忽略空值和错误
        BeanUtil.copyProperties(sysUser, dbSysUser, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 保存更新后的用户信息
        sysUserRepository.save(dbSysUser);
    }

    /**
     * 根据ID删除用户信息
     * @param id 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 根据ID删除用户
        sysUserRepository.deleteById(id);
    }

    /**
     * 更新个人密码
     * @param modifyPwdModel 包含用户ID和新旧密码的模型
     * @return 返回密码更新是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwd(ModifyPwdModel modifyPwdModel) {
        // 根据ID获取用户信息
        SysUser dbUser = sysUserRepository.getReferenceById(modifyPwdModel.getUserId());
        // 判断输入的旧密码是否正确
        String dbPwd = dbUser.getPassword();
        String usePwd = Md5Util.MD5(modifyPwdModel.getUsedPass());
        if (!usePwd.equals(dbPwd)) {
            return false; // 旧密码不正确，返回失败
        } else {
            // 对新密码进行MD5加密并更新
            String newPwd = Md5Util.MD5(modifyPwdModel.getNewPass());
            dbUser.setPassword(newPwd);
            sysUserRepository.save(dbUser); // 保存更新后的用户信息
            return true; // 返回成功
        }
    }
}
