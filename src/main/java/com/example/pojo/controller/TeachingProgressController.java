package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.pojo.domain.TeachingProgress;
import com.example.pojo.service.ITeachingProgressService;
import com.example.pojo.service.dto.TeachingProgressQueryCriteria;
import com.example.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：教学进度前端控制器
 * 该控制器负责处理与教学进度相关的HTTP请求，包括获取进度列表、进度添加、获取进度详情、更新进度信息、删除进度和获取进度趋势数据等功能。
 */
@RestController
@RequestMapping("teaching-progresses")
public class TeachingProgressController {
    
    @Autowired
    private ITeachingProgressService teachingProgressService;

    public TeachingProgressController(ITeachingProgressService teachingProgressService) {
        this.teachingProgressService = teachingProgressService;
    }
    
    /**
     * 获取教学进度列表
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回教学进度列表
     */
    @GetMapping
    public ResponseEntity<Object> getList(TeachingProgressQueryCriteria queryCriteria, PageVo pageVo) {
        // 确保页码不小于0
        int pageIndex = Math.max(0, pageVo.getPageIndex() - 1);
        // 确保每页大小不小于1
        int pageSize = Math.max(1, pageVo.getPageSize());
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "id");
        // 返回教学进度列表
        return new ResponseEntity<>(teachingProgressService.getList(queryCriteria, pageable), HttpStatus.OK);
    }
    
    /**
     * 根据ID获取教学进度详情
     * @param id 教学进度ID
     * @return 返回教学进度详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return new ResponseEntity<>(teachingProgressService.findById(id), HttpStatus.OK);
    }
    
    /**
     * 新增教学进度
     * @param teachingProgress 教学进度对象
     * @return 返回操作结果
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TeachingProgress teachingProgress) {
        try {
            TeachingProgress created = teachingProgressService.create(teachingProgress);
            return new ResponseEntity<>(BaseResult.success("新增教学进度成功", created), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("新增教学进度失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 更新教学进度
     * @param id 教学进度ID
     * @param teachingProgress 教学进度对象
     * @return 返回操作结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody TeachingProgress teachingProgress) {
        try {
            teachingProgress.setId(id); // 确保ID正确
            teachingProgressService.update(teachingProgress);
            return new ResponseEntity<>(BaseResult.success("更新教学进度成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("更新教学进度失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 删除教学进度
     * @param id 教学进度ID
     * @return 返回操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            teachingProgressService.delete(id);
            return new ResponseEntity<>(BaseResult.success("删除教学进度成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("删除教学进度失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 获取教学计划的进度趋势数据
     * @param planId 教学计划ID
     * @return 返回进度趋势数据
     */
    @GetMapping("/trend/{planId}")
    public ResponseEntity<Object> getPlanProgressTrend(@PathVariable Long planId) {
        try {
            List<Map<String, Object>> trendData = teachingProgressService.getPlanProgressTrend(planId);
            return new ResponseEntity<>(BaseResult.success("获取进度趋势数据成功", trendData), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("获取进度趋势数据失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 计算指定日期的预期进度（兼容旧API路径）
     * 这个方法用于支持前端使用/teaching/progress/expected路径的请求
     * @param planId 教学计划ID
     * @param date 指定日期
     * @return 返回预期进度百分比
     */
    @GetMapping("/expected")
    public ResponseEntity<Object> calculateExpectedProgress(
            @RequestParam Long planId,
            @RequestParam String date) {
        try {
            // 转换日期格式
            LocalDate localDate = LocalDate.parse(date);
            BigDecimal expectedProgress = teachingProgressService.calculateExpectedProgress(planId, localDate);
            return new ResponseEntity<>(BaseResult.success("计算预期进度成功", expectedProgress), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("计算预期进度失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 获取预警列表
     * @param warningType 预警类型
     * @param status 处理状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param pageVo 分页信息
     * @return 返回预警列表
     */
    @GetMapping("/warning/list")
    public ResponseEntity<Object> getWarningList(
            @RequestParam(required = false) String warningType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            PageVo pageVo) {
        try {
            // 确保页码不小于0
            int pageIndex = Math.max(0, pageVo.getPageIndex() - 1);
            // 确保每页大小不小于1
            int pageSize = Math.max(1, pageVo.getPageSize());
            // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
            Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "id");
            
            // 获取预警列表
            Map<String, Object> warningList = teachingProgressService.getWarningList(warningType, status, startDate, endDate, pageable);
            return new ResponseEntity<>(BaseResult.success("获取预警列表成功", warningList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("获取预警列表失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 处理预警
     * @param id 预警记录ID
     * @param processRequest 处理请求
     * @return 返回操作结果
     */
    @PostMapping("/warning/{id}/process")
    public ResponseEntity<Object> processWarning(
            @PathVariable Long id,
            @RequestBody ProcessWarningRequest processRequest) {
        try {
            teachingProgressService.processWarning(id, processRequest.getProcessMethod(), processRequest.getProcessRemark());
            return new ResponseEntity<>(BaseResult.success("处理预警成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("处理预警失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 预警处理请求类
     */
    static class ProcessWarningRequest {
        private String processMethod;
        private String processRemark;
        
        public String getProcessMethod() {
            return processMethod;
        }
        
        public void setProcessMethod(String processMethod) {
            this.processMethod = processMethod;
        }
        
        public String getProcessRemark() {
            return processRemark;
        }
        
        public void setProcessRemark(String processRemark) {
            this.processRemark = processRemark;
        }
    }

    @DeleteMapping("/warning/batch")
    public ResponseEntity<Object> deleteWarningsBatch(@RequestBody List<Long> ids) {
        try {
            teachingProgressService.deleteWarningsBatch(ids);
            return new ResponseEntity<>(BaseResult.success("批量删除预警成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResult.fail("批量删除预警失败: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
} 