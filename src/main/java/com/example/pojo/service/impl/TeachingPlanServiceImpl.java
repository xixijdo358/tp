package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.Course;
import com.example.pojo.domain.GradeClass;
import com.example.pojo.domain.Teacher;
import com.example.pojo.domain.TeachingPlan;
import com.example.pojo.repository.CourseRepository;
import com.example.pojo.repository.GradeClassRepository;
import com.example.pojo.repository.TeacherRepository;
import com.example.pojo.repository.TeachingPlanRepository;
import com.example.pojo.service.ITeachingPlanService;
import com.example.pojo.service.dto.TeachingPlanQueryCriteria;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：教学计划服务实现
 */
@Service
@Transactional(readOnly = true)
public class TeachingPlanServiceImpl implements ITeachingPlanService {

    private final TeachingPlanRepository teachingPlanRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GradeClassRepository gradeClassRepository;

    public TeachingPlanServiceImpl(TeachingPlanRepository teachingPlanRepository,
                                 TeacherRepository teacherRepository,
                                 CourseRepository courseRepository,
                                 GradeClassRepository gradeClassRepository) {
        this.teachingPlanRepository = teachingPlanRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.gradeClassRepository = gradeClassRepository;
    }

    /**
     * 获取所有教学计划
     * @param queryCriteria 查询条件
     * @param pageable 分页参数
     * @return 返回教学计划分页数据
     */
    @Override
    public Object getList(TeachingPlanQueryCriteria queryCriteria, Pageable pageable) {
        Page<TeachingPlan> page = teachingPlanRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page);
    }

    /**
     * 根据ID获取教学计划详情
     * @param id 教学计划ID
     * @return 返回教学计划详情
     */
    @Override
    public TeachingPlan findById(Long id) {
        return teachingPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("教学计划不存在"));
    }

    /**
     * 新增教学计划
     * @param teachingPlan 教学计划对象
     * @return 返回新增的教学计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeachingPlan create(TeachingPlan teachingPlan) {
        // 验证教师ID
        if (teachingPlan.getTeacher() != null && teachingPlan.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(teachingPlan.getTeacher().getId())
                    .orElseThrow(() -> new IllegalArgumentException("教师不存在"));
            teachingPlan.setTeacher(teacher);
        }
        
        // 验证课程ID
        if (teachingPlan.getCourse() != null && teachingPlan.getCourse().getId() != null) {
            Course course = courseRepository.findById(teachingPlan.getCourse().getId())
                    .orElseThrow(() -> new IllegalArgumentException("课程不存在"));
            teachingPlan.setCourse(course);
        }
        
        // 验证班级ID
        if (teachingPlan.getClassInfo() != null && teachingPlan.getClassInfo().getId() != null) {
            GradeClass gradeClass = gradeClassRepository.findById(teachingPlan.getClassInfo().getId())
                    .orElseThrow(() -> new IllegalArgumentException("班级不存在"));
            teachingPlan.setClassInfo(gradeClass);
        }
        
        return teachingPlanRepository.save(teachingPlan);
    }

    /**
     * 更新教学计划
     * @param teachingPlan 教学计划对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TeachingPlan teachingPlan) {
        // 查询原有教学计划
        TeachingPlan dbTeachingPlan = teachingPlanRepository.findById(teachingPlan.getId())
                .orElseThrow(() -> new IllegalArgumentException("教学计划不存在"));
        
        // 处理关联实体
        if (teachingPlan.getTeacher() != null && teachingPlan.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(teachingPlan.getTeacher().getId())
                    .orElseThrow(() -> new IllegalArgumentException("教师不存在"));
            teachingPlan.setTeacher(teacher);
        }
        
        if (teachingPlan.getCourse() != null && teachingPlan.getCourse().getId() != null) {
            Course course = courseRepository.findById(teachingPlan.getCourse().getId())
                    .orElseThrow(() -> new IllegalArgumentException("课程不存在"));
            teachingPlan.setCourse(course);
        }
        
        if (teachingPlan.getClassInfo() != null && teachingPlan.getClassInfo().getId() != null) {
            GradeClass gradeClass = gradeClassRepository.findById(teachingPlan.getClassInfo().getId())
                    .orElseThrow(() -> new IllegalArgumentException("班级不存在"));
            teachingPlan.setClassInfo(gradeClass);
        }
        
        // 复制属性，忽略空值和错误
        BeanUtil.copyProperties(teachingPlan, dbTeachingPlan, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        
        // 保存更新
        teachingPlanRepository.save(dbTeachingPlan);
    }

    /**
     * 删除教学计划
     * @param id 教学计划ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        teachingPlanRepository.deleteById(id);
    }

    /**
     * 功能描述：导出教学计划数据
     * @param queryAll 是否查询全部数据
     * @param queryCriteria 查询条件
     * @return 导出数据列表
     */
    @Override
    public List<Map<String, Object>> exportData(Boolean queryAll, TeachingPlanQueryCriteria queryCriteria) {
        // 根据条件查询数据
        List<TeachingPlan> planList;
        if (queryAll != null && queryAll) {
            // 查询全部数据
            planList = teachingPlanRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                    QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder));
        } else {
            // 使用分页查询
            Pageable pageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "id"));
            Page<TeachingPlan> page = teachingPlanRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                    QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
            planList = page.getContent();
        }

        // 将数据转换为Map列表
        List<Map<String, Object>> result = new ArrayList<>();
        for (TeachingPlan plan : planList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", plan.getId());
            map.put("计划标题", plan.getTitle());
            map.put("计划内容", plan.getContent());
            map.put("开始日期", plan.getStartDate());
            map.put("结束日期", plan.getEndDate());
            map.put("总课时", plan.getTotalLessons());
            
            // 添加关联对象信息
            if (plan.getTeacher() != null) {
                map.put("教师", plan.getTeacher().getName());
            } else {
                map.put("教师", "");
            }
            
            if (plan.getCourse() != null) {
                map.put("课程", plan.getCourse().getCoursename());
            } else {
                map.put("课程", "");
            }
            
            if (plan.getClassInfo() != null) {
                map.put("班级", plan.getClassInfo().getName());
            } else {
                map.put("班级", "");
            }
            
            map.put("创建时间", plan.getCreateTime());
            map.put("更新时间", plan.getUpdateTime());
            
            result.add(map);
        }
        
        return result;
    }
}
