package com.example.pojo.repository;

import com.example.pojo.domain.TeachingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能描述：教学进度数据访问接口
 */
public interface TeachingProgressRepository extends JpaRepository<TeachingProgress, Long>, JpaSpecificationExecutor<TeachingProgress> {
    
    /**
     * 根据教学计划ID获取进度记录列表，按记录日期排序
     * @param planId 教学计划ID
     * @return 进度记录列表
     */
    @Query("SELECT p FROM TeachingProgress p WHERE p.teachingPlan.id = :planId ORDER BY p.recordDate")
    List<TeachingProgress> findByTeachingPlanIdOrderByRecordDate(@Param("planId") Long planId);
    
    /**
     * 根据教学计划ID和记录日期查询进度记录
     * @param planId 教学计划ID
     * @param recordDate 记录日期
     * @return 进度记录
     */
    @Query("SELECT p FROM TeachingProgress p WHERE p.teachingPlan.id = :planId AND p.recordDate = :recordDate")
    TeachingProgress findByTeachingPlanIdAndRecordDate(@Param("planId") Long planId, @Param("recordDate") LocalDate recordDate);
    
    /**
     * 计算预期进度百分比
     * 根据教学计划的开始日期、结束日期和指定日期，计算理论上应该达到的进度百分比
     * @param planId 教学计划ID
     * @param date 指定日期
     * @return 预期进度百分比
     */
    @Query(value = "SELECT " +
            "CASE " +
            "WHEN :date < p.start_date THEN 0 " +
            "WHEN :date > p.end_date THEN 100 " +
            "ELSE ROUND(((TO_DAYS(:date) - TO_DAYS(p.start_date)) / (TO_DAYS(p.end_date) - TO_DAYS(p.start_date))) * 100, 2) " +
            "END " +
            "FROM teaching_plan p WHERE p.id = :planId", nativeQuery = true)
    BigDecimal calculateExpectedProgress(@Param("planId") Long planId, @Param("date") LocalDate date);
} 