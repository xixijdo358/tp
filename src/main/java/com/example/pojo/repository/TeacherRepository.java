package com.example.pojo.repository;

import com.example.pojo.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：教师管理持久层
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
}