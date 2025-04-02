package com.example.pojo.repository;

import com.example.pojo.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：课程信息持久层
 */
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
}