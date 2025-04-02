package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.Course;
import com.example.pojo.service.ICourseService;
import com.example.pojo.service.dto.CourseQueryCriteria;
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

/**功能描述：课程信息前端控制器
 * 该控制器负责处理与课程相关的HTTP请求，包括获取课程列表、添加课程、获取课程详情、更新课程和删除课程等功能。
 */
@RestController
@RequestMapping("course")
public class CourseController {

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService; // 构造函数注入课程服务
    }

    /**
     * 获取课程列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回课程列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(CourseQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回课程列表
        return new ResponseEntity<>(courseService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 添加课程信息
     * @param course 课程对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addCourse(@RequestBody Course course){
        boolean result = courseService.addCourse(course); // 调用服务添加课程
        if(result){
            return BaseResult.success("添加成功"); // 返回成功信息
        } else {
            return BaseResult.fail("添加失败"); // 返回失败信息
        }
    }

    /**
     * 根据ID获取课程详情信息
     * @param id 课程ID
     * @return 返回课程详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("获取信息失败"); // 如果ID为空，抛出异常
        }
        Course dbCourse = courseService.getById(id); // 根据ID获取课程
        return BaseResult.success(dbCourse); // 返回课程详情
    }

    /**
     * 更新课程信息
     * @param course 课程对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editCourse(@RequestBody Course course){
        courseService.editCourse(course); // 调用服务更新课程
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除课程信息
     * @param id 课程ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        courseService.deleteById(id); // 根据ID删除课程
        return BaseResult.success("删除成功"); // 返回成功信息
    }

    /**
     * 获取所有课程信息
     * @return 返回所有课程信息
     */
    @GetMapping(value = "/all")
    public BaseResult getAll(){
        List<Course> list = courseService.queryAll(); // 查询所有课程
        // 将课程列表转换为结果对象列表
        List<ResultVo> result = list.stream().map(temp -> {
            ResultVo obj = new ResultVo();
            obj.setName(temp.getCoursename()); // 设置课程名称
            obj.setId(temp.getId()); // 设置课程ID
            return obj; // 返回结果对象
        }).collect(Collectors.toList());
        return BaseResult.success(result); // 返回成功信息
    }
}
