package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.Student;
import com.example.pojo.domain.GradeClass;
import com.example.pojo.repository.StudentRepository;
import com.example.pojo.service.IStudentService;
import com.example.pojo.service.dto.StudentQueryCriteria;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**功能描述：学生信息业务接口实现类
 */
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 获取学生列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回学生列表的分页数据
     */
    @Override
    public Object getList(StudentQueryCriteria queryCriteria, Pageable pageable) {
        // 使用QueryHelp构建查询条件，并从数据库中获取学生数据
        Page<Student> page = studentRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        // 将获取的分页数据转换为所需格式并返回
        return PageUtil.toPage(page);
    }

    /**
     * 添加学生信息
     * @param student 学生对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addStudent(Student student) {
        // 处理班级关联，避免级联持久化已存在的班级
        if (student.getGradeClass() != null && student.getGradeClass().getId() != null) {
            // 只设置班级ID的引用，不尝试级联保存班级实体
            GradeClass gradeClass = new GradeClass();
            gradeClass.setId(student.getGradeClass().getId());
            student.setGradeClass(gradeClass);
        }
        
        // 保存学生信息到数据库
        Student dbStudent = studentRepository.save(student);
        // 检查学生ID是否不为空以确认添加成功
        return dbStudent.getId() != null;
    }

    /**
     * 根据ID获取学生详情信息
     * @param id 学生ID
     * @return 返回学生对象
     */
    @Override
    public Student getById(Long id) {
        // 根据ID查找学生，如果未找到则返回一个新的学生对象
        return studentRepository.findById(id).orElseGet(Student::new);
    }

    /**
     * 更新学生信息
     * @param student 学生对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editStudent(Student student) {
        // 获取数据库中对应的学生对象
        Student dbStudent = studentRepository.getReferenceById(student.getId());
        
        // 保存班级引用，避免被BeanUtil.copyProperties覆盖
        GradeClass gradeClass = null;
        if (student.getGradeClass() != null && student.getGradeClass().getId() != null) {
            gradeClass = new GradeClass();
            gradeClass.setId(student.getGradeClass().getId());
            dbStudent.setGradeClass(gradeClass);
        }
        
        // 复制属性，忽略空值和错误
        BeanUtil.copyProperties(student, dbStudent, CopyOptions.create()
                .setIgnoreNullValue(true)
                .setIgnoreError(true)
                .setIgnoreProperties("gradeClass")); // 忽略gradeClass属性
        
        // 保存更新后的学生信息
        studentRepository.save(dbStudent);

    }

    /**
     * 根据ID删除学生信息
     * @param id 学生ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 从数据库中删除对应ID的学生信息
        studentRepository.deleteById(id);
    }

    /**
     * 统计人数
     * @return 返回学生总人数
     */
    @Override
    public long getCount() {
        // 返回学生的总数量
        return studentRepository.count();
    }
}
