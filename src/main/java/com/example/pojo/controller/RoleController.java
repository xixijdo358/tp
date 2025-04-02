package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.SysRole;
import com.example.pojo.service.IRoleService;
import com.example.pojo.service.dto.RoleQueryCriteria;
import com.example.utils.PageVo;
import com.example.utils.ResultVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**功能描述：系统角色前端控制器
 * 该控制器负责处理与角色相关的HTTP请求，包括获取角色列表、添加角色、获取角色详情、更新角色和删除角色等功能。
 */
@RestController
@RequestMapping("role")
public class RoleController {

    private final IRoleService roleService; // 角色服务接口

    public RoleController(IRoleService roleService) {
        this.roleService = roleService; // 构造函数注入角色服务
    }

    /**
     * 获取角色列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回角色列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(RoleQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回角色列表
        return new ResponseEntity<>(roleService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 添加角色信息
     * @param sysRole 角色对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addRole(@RequestBody SysRole sysRole){
        boolean result = roleService.addRole(sysRole); // 调用服务添加角色
        if(result){
            return BaseResult.success("添加成功"); // 返回成功信息
        } else {
            return BaseResult.fail("添加失败"); // 返回失败信息
        }
    }

    /**
     * 根据ID获取角色详情信息
     * @param id 角色ID
     * @return 返回角色详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("获取信息失败"); // 如果ID为空，抛出异常
        }
        SysRole dbSysRole = roleService.getById(id); // 从服务获取角色信息
        return BaseResult.success(dbSysRole); // 返回角色信息
    }

    /**
     * 更新角色信息
     * @param sysRole 角色对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editRole(@RequestBody SysRole sysRole){
        roleService.editRole(sysRole); // 调用服务更新角色
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除角色信息
     * @param id 角色ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        roleService.deleteById(id); // 调用服务删除角色
        return BaseResult.success("删除成功"); // 返回成功信息
    }

    /**
     * 获取所有角色信息
     * @return 返回所有角色信息
     */
    @GetMapping(value = "/all")
    public BaseResult getAll(){
        List<SysRole> list = roleService.queryAll(); // 获取所有角色
        List<ResultVo> result = list.stream().map(temp -> {
            ResultVo obj = new ResultVo(); // 创建结果对象
            obj.setName(temp.getName()); // 设置角色名称
            obj.setId(temp.getId()); // 设置角色ID
            return obj; // 返回结果对象
        }).collect(Collectors.toList()); // 收集结果
        return BaseResult.success(result); // 返回成功信息和角色列表
    }
}
