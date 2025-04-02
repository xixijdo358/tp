package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.Course;

import com.example.pojo.repository.CourseRepository;
import com.example.pojo.service.ICourseService;
import com.example.pojo.service.dto.CourseQueryCriteria;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**功能描述：课程信息业务接口实现类
 */
@Service
@Transactional(readOnly = true)
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * 获取课程列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回课程列表的分页数据
     */
    @Override
    public Object getList(CourseQueryCriteria queryCriteria, Pageable pageable) {
        // 使用QueryHelp构建查询条件，并从数据库中获取课程数据
        Page<Course> page = courseRepository.findAll((root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        // 将获取的分页数据转换为所需格式并返回
        return PageUtil.toPage(page);
    }

    /**
     * 添加课程信息
     * @param course 课程对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCourse(Course course) {
        // 保存课程信息到数据库
        Course dbCourse = courseRepository.save(course);
        // 检查课程ID是否不为空以确认添加成功
        return dbCourse.getId() != null;
    }

    /**
     * 根据id获取课程信息
     * @param id 课程ID
     * @return 返回课程对象
     */
    @Override
    public Course getById(Long id) {
        // 根据ID查找课程，如果未找到则返回一个新的课程对象
        return courseRepository.findById(id).orElseGet(Course::new);
    }

    /**
     * 更新课程信息
     * @param course 课程对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editCourse(Course course) {
        // 获取数据库中对应的课程对象
        Course dbCourse = courseRepository.getReferenceById(course.getId());
        // 复制传入的课程信息到数据库对象中，忽略空值和错误
        BeanUtil.copyProperties(course, dbCourse, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 保存更新后的课程对象
        courseRepository.save(dbCourse);
    }

    /**
     * 根据id删除课程信息
     * @param id 课程ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 根据ID删除课程信息
        courseRepository.deleteById(id);
    }

    /**
     * 获取所有课程
     * @return 返回所有课程的列表
     */
    @Override
    public List<Course> queryAll() {
        // 查询并返回所有课程
        return courseRepository.findAll();
    }

    /**
     * 统计课程门数
     * @return 返回课程数量
     */
    @Override
    public long getCount() {
        // 返回课程的总数
        return courseRepository.count();
    }
}
