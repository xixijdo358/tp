package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.pojo.domain.TeachingPlan;
import com.example.pojo.service.ITeachingPlanService;
import com.example.pojo.service.dto.TeachingPlanQueryCriteria;
import com.example.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：教学计划前端控制器
 * 该控制器负责处理与教学计划相关的HTTP请求，包括获取计划列表、计划添加、获取计划详情、更新计划信息和删除计划等功能。
 */
@RestController
@RequestMapping("teaching-plans")
public class TeachingPlanController {
    
    @Autowired
    private ITeachingPlanService teachingPlanService;

    public TeachingPlanController(ITeachingPlanService teachingPlanService) {
        this.teachingPlanService = teachingPlanService;
    }
    
    /**
     * 获取教学计划列表
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回教学计划列表
     */
    @GetMapping
    public ResponseEntity<Object> getList(TeachingPlanQueryCriteria queryCriteria, PageVo pageVo) {
        // 确保页码不小于0
        int pageIndex = Math.max(0, pageVo.getPageIndex() - 1);
        // 确保每页大小不小于1
        int pageSize = Math.max(1, pageVo.getPageSize());
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "id");
        // 返回教学计划列表
        return new ResponseEntity<>(teachingPlanService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 根据ID获取教学计划详情
     * @param id 教学计划ID
     * @return 返回教学计划详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return new ResponseEntity<>(teachingPlanService.findById(id), HttpStatus.OK);
    }

    /**
     * 新增教学计划
     * @param teachingPlan 教学计划对象
     * @return 返回操作结果
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TeachingPlan teachingPlan) {
        try {
            TeachingPlan created = teachingPlanService.create(teachingPlan);
            return new ResponseEntity<>(BaseResult.success("新增教学计划成功", created), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("新增教学计划失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 更新教学计划
     * @param id 教学计划ID
     * @param teachingPlan 教学计划对象
     * @return 返回操作结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody TeachingPlan teachingPlan) {
        try {
            teachingPlan.setId(id); // 确保ID正确
            teachingPlanService.update(teachingPlan);
            return new ResponseEntity<>(BaseResult.success("更新教学计划成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("更新教学计划失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 删除教学计划
     * @param id 教学计划ID
     * @return 返回操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            teachingPlanService.delete(id);
            return new ResponseEntity<>(BaseResult.success("删除教学计划成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("删除教学计划失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
