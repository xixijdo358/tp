package com.example.pojo.repository;

import com.example.pojo.domain.GradeClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 功能描述：班级管理持久层
 */
public interface GradeClassRepository extends JpaRepository<GradeClass, Long>, JpaSpecificationExecutor<GradeClass> {
}