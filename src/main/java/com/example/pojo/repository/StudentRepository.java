package com.example.pojo.repository;

import com.example.pojo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 功能描述：学生管理持久层
 */
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    /**
     * 根据班级ID获取所有学生列表
     * @param gradeClassId
     * @return
     */
    List<Student> findAllByGradeClassId(Long gradeClassId);
}