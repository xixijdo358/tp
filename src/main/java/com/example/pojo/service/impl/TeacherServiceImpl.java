package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.Teacher;
import com.example.pojo.repository.TeacherRepository;
import com.example.pojo.service.ITeacherService;
import com.example.pojo.service.dto.TeacherQueryCriteria;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**功能描述：教师信息业务接口实现类
 * 该类实现了ITeacherService接口，提供了与教师相关的业务逻辑。
 * 包含获取教师列表、添加教师、获取教师详情、更新教师信息、删除教师和统计教师个数等功能。
 */
@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository; // 教师数据访问接口

    // 构造函数，注入教师的仓库
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * 获取教师列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回分页的教师列表
     */
    @Override
    public Object getList(TeacherQueryCriteria queryCriteria, Pageable pageable) {
        // 根据查询条件和分页信息获取教师数据
        Page<Teacher> page = teacherRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page); // 转换为分页格式并返回
    }

    /**
     * 添加教师信息
     * @param teacher 教师对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeacher(Teacher teacher) {
        // 保存教师信息到数据库
        Teacher dbTeacher = teacherRepository.save(teacher);
        // 检查教师ID是否不为空以确认添加成功
        return dbTeacher.getId() != null;
    }

    /**
     * 获取教师信息
     * @param id 教师ID
     * @return 返回教师对象
     */
    @Override
    public Teacher getById(Long id) {
        // 根据ID查找教师，如果未找到则返回一个新的教师对象
        return teacherRepository.findById(id).orElseGet(Teacher::new);
    }

    /**
     * 更新教师信息
     * @param teacher 教师对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTeacher(Teacher teacher) {
        // 获取数据库中对应的教师对象
        Teacher dbTeacher = teacherRepository.getReferenceById(teacher.getId());
        // 复制教师信息，忽略空值和错误
        BeanUtil.copyProperties(teacher, dbTeacher, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 保存更新后的教师信息
        teacherRepository.save(dbTeacher);
    }

    /**
     * 根据ID删除教师信息
     * @param id 教师ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 根据ID删除教师
        teacherRepository.deleteById(id);
    }

    /**
     * 统计教师个数
     * @return 返回教师总数
     */
    @Override
    public long getCount() {
        // 返回教师的总数量
        return teacherRepository.count();
    }
}
