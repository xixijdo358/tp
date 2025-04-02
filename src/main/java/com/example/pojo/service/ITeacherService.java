package com.example.pojo.service;

import com.example.pojo.domain.Teacher;
import com.example.pojo.service.dto.TeacherQueryCriteria;
import org.springframework.data.domain.Pageable;

/** 
 * 功能描述：教师信息业务接口
 * 该接口定义了与教师信息相关的业务逻辑，包括获取教师列表、添加教师、获取教师详情、更新教师信息、删除教师以及统计教师人数等功能。
 */
public interface ITeacherService {

    /**
     * 获取教师列表数据
     * @param queryCriteria 查询条件，用于过滤教师信息
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的教师列表
     */
    Object getList(TeacherQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加教师信息
     * @param teacher 教师对象，包含教师的详细信息
     * @return 返回添加是否成功
     */
    boolean addTeacher(Teacher teacher);

    /**
     * 获取教师信息
     * @param id 教师ID
     * @return 返回对应的教师对象
     */
    Teacher getById(Long id);

    /**
     * 更新教师信息
     * @param teacher 教师对象，包含更新后的教师信息
     */
    void editTeacher(Teacher teacher);

    /**
     * 根据ID删除教师信息
     * @param id 教师ID
     */
    void deleteById(Long id);

    /**
     * 统计教师个数
     * @return 返回教师的总人数
     */
    long getCount();
}
