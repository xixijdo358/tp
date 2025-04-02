package com.example.pojo.service;

import com.example.pojo.domain.TeachingProgress;
import com.example.pojo.service.dto.TeachingProgressQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：教学进度服务接口
 */
public interface ITeachingProgressService {
  

      /**
     * 获取所有教学计划
     * @param queryCriteria 查询条件
     * @param pageable 分页参数
     * @return 返回符合条件教学计划列表
     */
    Object getList(TeachingProgressQueryCriteria queryCriteria, Pageable pageable);

    
    /**
     * 根据ID获取教学进度详情
     * @param id 教学进度ID
     * @return 教学进度详情
     */
    TeachingProgress findById(Long id);
    
    /**
     * 创建教学进度记录
     * @param teachingProgress 教学进度对象
     * @return 创建后的教学进度对象
     */
    TeachingProgress create(TeachingProgress teachingProgress);
    
    /**
     * 更新教学进度记录
     * @param teachingProgress 教学进度对象
     */
    void update(TeachingProgress teachingProgress);
    
    /**
     * 删除教学进度记录
     * @param id 教学进度ID
     */
    void delete(Long id);
    
    /**
     * 根据教学计划ID获取进度趋势数据
     * @param planId 教学计划ID
     * @return 进度趋势数据
     */
    List<Map<String, Object>> getPlanProgressTrend(Long planId);
    
    /**
     * 计算指定日期的预期进度
     * @param planId 教学计划ID
     * @param date 指定日期
     * @return 预期进度百分比
     */
    BigDecimal calculateExpectedProgress(Long planId, LocalDate date);
    
    /**
     * 导出教学进度数据
     * @param queryAll 是否导出所有数据
     * @param criteria 查询条件
     * @return 导出数据列表
     */
    List<Map<String, Object>> exportData(Boolean queryAll, TeachingProgressQueryCriteria criteria) throws IOException;
    
    /**
     * 生成教学进度差异记录
     * @param progress 教学进度对象
     */
    void generateProgressDifference(TeachingProgress progress);
    
    /**
     * 获取预警列表
     * @param warningType 预警类型
     * @param status 处理状态 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param pageable 分页参数
     * @return 预警列表数据
     */
    Map<String, Object> getWarningList(String warningType, Integer status, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    /**
     * 处理预警
     * @param id 预警记录ID
     * @param processMethod 处理方式
     * @param processRemark 处理说明
     */
    void processWarning(Long id, String processMethod, String processRemark);

    void deleteWarningsBatch(List<Long> ids);
} 