package com.example.pojo.repository;

import com.example.pojo.domain.TeachingProgressDifference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * 功能描述：教学进度差异数据访问接口
 */
public interface TeachingProgressDifferenceRepository extends JpaRepository<TeachingProgressDifference, Long>, JpaSpecificationExecutor<TeachingProgressDifference> {
    
    /**
     * 根据教学计划ID获取差异记录列表
     * @param planId 教学计划ID
     * @return 差异记录列表
     */
    @Query("SELECT d FROM TeachingProgressDifference d WHERE d.teachingPlan.id = :planId ORDER BY d.createTime DESC")
    List<TeachingProgressDifference> findByTeachingPlanIdOrderByCreateTimeDesc(@Param("planId") Long planId);
    
    /**
     * 根据教学进度ID获取差异记录列表
     * @param progressId 教学进度ID
     * @return 差异记录列表
     */
    @Query("SELECT d FROM TeachingProgressDifference d WHERE d.teachingProgress.id = :progressId")
    List<TeachingProgressDifference> findByTeachingProgressId(@Param("progressId") Long progressId);
    
    /**
     * 根据教学计划ID获取最新的预警记录
     * @param planId 教学计划ID
     * @return 预警记录列表
     */
    @Query("SELECT d FROM TeachingProgressDifference d WHERE d.teachingPlan.id = :planId AND d.warning = true AND d.status = 1 ORDER BY d.createTime DESC")
    List<TeachingProgressDifference> findLatestWarningsByPlanId(@Param("planId") Long planId);
} 