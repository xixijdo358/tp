package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.Teacher;
import com.example.pojo.service.ITeacherService;
import com.example.pojo.service.dto.TeacherQueryCriteria;
import com.example.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**功能描述：教师信息前端控制器
 * 该控制器负责处理与教师相关的HTTP请求，包括获取教师列表、添加教师、获取教师详情、更新教师信息和删除教师等功能。
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {

    private final ITeacherService teacherService; // 教师服务接口

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService; // 构造函数注入教师服务
    }

    /**
     * 获取教师列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回教师列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(TeacherQueryCriteria queryCriteria, PageVo pageVo){
        // 确保页码不小于0
        int pageIndex = Math.max(0, pageVo.getPageIndex() - 1);
        // 确保每页大小不小于1
        int pageSize = Math.max(1, pageVo.getPageSize());
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "id");
        // 返回教师列表
        return new ResponseEntity<>(teacherService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 添加教师信息
     * @param teacher 教师对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addTeacher(@RequestBody Teacher teacher){
        boolean result = teacherService.addTeacher(teacher); // 调用服务添加教师
        if(result){
            return BaseResult.success("添加成功"); // 返回成功信息
        } else {
            return BaseResult.fail("添加失败"); // 返回失败信息
        }
    }

    /**
     * 根据ID获取教师详情信息
     * @param id 教师ID
     * @return 返回教师详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("获取信息失败"); // ID为空时抛出异常
        }
        Teacher dbTeacher = teacherService.getById(id); // 根据ID获取教师信息
        return BaseResult.success(dbTeacher); // 返回教师信息
    }

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editTeacher(@RequestBody Teacher teacher){
        teacherService.editTeacher(teacher); // 调用服务更新教师信息
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除教师信息
     * @param id 教师ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("删除信息失败"); // ID为空时抛出异常
        }
        teacherService.deleteById(id); // 调用服务删除教师信息
        return BaseResult.success("删除成功"); // 返回成功信息
    }

}
