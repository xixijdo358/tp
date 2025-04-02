package com.example.pojo.repository;

import com.example.pojo.domain.TeachingPlan; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository; 
import java.util.List; 

/**
 * 功能描述：教学计划管理持久层
 */
@Repository
public interface TeachingPlanRepository extends JpaRepository<TeachingPlan, Long>, JpaSpecificationExecutor<TeachingPlan> {
    /**
     * 功能描述：根据教师ID查询教学计划列表
     * @param teacherId 教师ID
     * @return 教学计划列表
     */
    List<TeachingPlan> findAllByTeacherId(Long teacherId);

    /**
     * 功能描述：根据课程ID查询教学计划列表
     * @param courseId 课程ID
     * @return 教学计划列表
     */
    List<TeachingPlan> findAllByCourseId(Long courseId);

    /**
     * 功能描述：根据班级ID查询教学计划列表
     * @param classId 班级ID
     * @return 教学计划列表
     */
    List<TeachingPlan> findAllByClassInfoId(Long classId);

    /**
     * 功能描述：根据教师ID和课程ID查询教学计划
     * @param teacherId 教师ID
     * @param courseId 课程ID
     * @return 教学计划列表
     */
    List<TeachingPlan> findAllByTeacherIdAndCourseId(Long teacherId, Long courseId);

    /**
     * 功能描述：根据教师ID、课程ID和班级ID查询教学计划
     * @param teacherId 教师ID
     * @param courseId 课程ID
     * @param classId 班级ID
     * @return 教学计划列表
     */
    List<TeachingPlan> findAllByTeacherIdAndCourseIdAndClassInfoId(Long teacherId, Long courseId, Long classId);

}