package com.example.pojo.service;

import com.example.pojo.domain.Course;
import com.example.pojo.service.dto.CourseQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/** 
 * 功能描述：课程信息业务接口
 * 该接口定义了与课程相关的业务逻辑，包括获取课程列表、添加课程、获取课程详情、更新课程信息、删除课程以及统计课程数量等功能。
 */
public interface ICourseService {
    /**
     * 获取课程列表数据
     * @param queryCriteria 查询条件，用于过滤课程
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的课程列表
     */
    Object getList(CourseQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加课程信息
     * @param course 课程对象，包含课程的详细信息
     * @return 返回添加是否成功
     */
    boolean addCourse(Course course);

    /**
     * 根据id获取课程信息
     * @param id 课程ID
     * @return 返回对应的课程对象
     */
    Course getById(Long id);

    /**
     * 更新课程信息
     * @param course 课程对象，包含更新后的课程信息
     */
    void editCourse(Course course);

    /**
     * 根据id删除课程信息
     * @param id 课程ID
     */
    void deleteById(Long id);

    /**
     * 获取所有课程
     * @return 返回所有课程的列表
     */
    List<Course> queryAll();

    /**
     * 统计课程门数
     * @return 返回课程的总数量
     */
    long getCount();
}
