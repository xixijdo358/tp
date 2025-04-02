package com.example.pojo.service;

import com.example.pojo.domain.Student;
import com.example.pojo.service.dto.StudentQueryCriteria;
import org.springframework.data.domain.Pageable;

/** 
 * 功能描述：学生信息业务接口
 * 该接口定义了与学生信息相关的业务逻辑，包括获取学生列表、添加学生、获取学生详情、更新学生信息、删除学生以及统计学生人数等功能。
 */
public interface IStudentService {
    /**
     * 获取学生列表数据
     * @param queryCriteria 查询条件，用于过滤学生信息
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的学生列表
     */
    Object getList(StudentQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加学生信息
     * @param student 学生对象，包含学生的详细信息
     * @return 返回添加是否成功
     */
    boolean addStudent(Student student);

    /**
     * 根据ID获取学生详情信息
     * @param id 学生ID
     * @return 返回对应的学生对象
     */
    Student getById(Long id);

    /**
     * 更新学生信息
     * @param student 学生对象，包含更新后的学生信息
     */
    void editStudent(Student student);

    /**
     * 根据ID删除学生信息
     * @param id 学生ID
     */
    void deleteById(Long id);

    /**
     * 统计学生人数
     * @return 返回学生的总人数
     */
    long getCount();
}
