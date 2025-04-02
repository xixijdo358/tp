package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.Student;
import com.example.pojo.domain.GradeClass;
import com.example.pojo.service.IStudentService;

import com.example.pojo.service.dto.StudentQueryCriteria;
import com.example.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**功能描述：学生信息前端控制器
 * 该控制器负责处理与学生相关的HTTP请求，包括获取学生列表、添加学生、获取学生详情、更新学生信息和删除学生等功能。
 */
@RestController
@RequestMapping("student")
public class StudentController {

    private final IStudentService studentService; // 学生服务接口

    public StudentController(IStudentService studentService) {
        this.studentService = studentService; // 构造函数注入学生服务
    }

    /**
     * 获取学生列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回学生列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(StudentQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回学生列表
        return new ResponseEntity<>(studentService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 添加学生信息
     * @param student 学生对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addStudent(@RequestBody Student student){
        try {
            boolean result = studentService.addStudent(student); // 调用服务添加学生
            if(result){
                return BaseResult.success("添加成功"); // 返回成功信息
            } else {
                return BaseResult.fail("添加失败"); // 返回失败信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("添加失败: " + e.getMessage()); // 返回详细错误信息
        }
    }

    /**
     * 根据ID获取学生详情信息
     * @param id 学生ID
     * @return 返回学生详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("获取信息失败"); // 如果ID为空，抛出异常
        }
        Student dbStudent = studentService.getById(id); // 根据ID获取学生信息
        return BaseResult.success(dbStudent); // 返回学生详情
    }

    /**
     * 更新学生信息
     * @param student 学生对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult update(@RequestBody Student student){
        if(student.getId() == null){
            return BaseResult.fail("学生ID不能为空"); // 如果ID为空，返回失败信息
        }
        try {
            studentService.editStudent(student); // 调用服务更新学生信息
            return BaseResult.success("更新成功"); // 返回成功信息
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("更新失败: " + e.getMessage()); // 返回详细错误信息
        }
    }

    /**
     * 根据ID删除学生信息
     * @param id 学生ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        studentService.deleteById(id); // 调用服务删除学生信息
        return BaseResult.success("删除成功"); // 返回成功信息
    }

}
