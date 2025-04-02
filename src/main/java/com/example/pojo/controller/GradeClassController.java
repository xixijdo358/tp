package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.GradeClass;
import com.example.pojo.service.IGradeClassService;
import com.example.pojo.service.dto.GradeClassQueryCriteria;

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

/**功能描述：班级信息前端控制器
 * 该控制器负责处理与班级相关的HTTP请求，包括获取班级列表、添加班级、获取班级详情、更新班级和删除班级等功能。
 */
@RestController
@RequestMapping("gradeclass")
public class GradeClassController {

    private final IGradeClassService gradeClassService;

    public GradeClassController(IGradeClassService gradeClassService) {
        this.gradeClassService = gradeClassService; // 构造函数注入班级服务
    }

    /**
     * 获取班级列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回班级列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(GradeClassQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回班级列表
        return new ResponseEntity<>(gradeClassService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 添加班级信息
     * @param gradeClass 班级对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addGradeClass(@RequestBody GradeClass gradeClass){
        boolean result = gradeClassService.addGradeClass(gradeClass); // 调用服务添加班级
        if(result){
            return BaseResult.success("添加成功"); // 返回成功信息
        } else {
            return BaseResult.fail("添加失败"); // 返回失败信息
        }
    }

    /**
     * 根据ID获取班级详情信息
     * @param id 班级ID
     * @return 返回班级详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("获取信息失败"); // 如果ID为空，抛出异常
        }
        GradeClass dbGradeClass = gradeClassService.getById(id); // 根据ID获取班级信息
        return BaseResult.success(dbGradeClass); // 返回班级详情
    }

    /**
     * 更新班级信息
     * @param gradeClass 班级对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editGradeClass(@RequestBody GradeClass gradeClass){
        gradeClassService.editGradeClass(gradeClass); // 调用服务更新班级信息
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除班级信息
     * @param id 班级ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null == id){
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        gradeClassService.deleteById(id); // 根据ID删除班级信息
        return BaseResult.success("删除成功"); // 返回成功信息
    }

    /**
     * 获取所有班级信息
     * @return 返回所有班级信息
     */
    @GetMapping(value = "/all")
    public BaseResult getAll(){
        List<GradeClass> list = gradeClassService.queryAll(new GradeClassQueryCriteria()); // 查询所有班级信息
        List<ResultVo> result = list.stream().map(temp -> {
            ResultVo obj = new ResultVo(); // 创建ResultVo对象
            obj.setName(temp.getName()); // 设置班级名称
            obj.setId(temp.getId()); // 设置班级ID
            return obj; // 返回ResultVo对象
        }).collect(Collectors.toList()); // 收集结果
        return BaseResult.success(result); // 返回所有班级信息
    }

}
