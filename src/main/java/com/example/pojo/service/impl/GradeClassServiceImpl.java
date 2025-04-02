package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.GradeClass;

import com.example.pojo.repository.GradeClassRepository;
import com.example.pojo.service.IGradeClassService;
import com.example.pojo.service.dto.GradeClassQueryCriteria;

import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**功能描述：班级信息业务实现类
 */
@Service
@Transactional(readOnly = true)
public class GradeClassServiceImpl implements IGradeClassService {

    private final GradeClassRepository gradeClassRepository;

    public GradeClassServiceImpl(GradeClassRepository gradeClassRepository) {
        this.gradeClassRepository = gradeClassRepository;
    }

    /**
     * 获取班级列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回班级列表的分页数据
     */
    @Override
    public Object getList(GradeClassQueryCriteria queryCriteria, Pageable pageable) {
       // 使用QueryHelp构建查询条件，并从数据库中获取班级数据
       Page<GradeClass> page = gradeClassRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, queryCriteria, cb)), pageable);
       // 将获取的分页数据转换为所需格式并返回
       return PageUtil.toPage(page);
    }

    /**
     * 新增班级信息
     * @param gradeClass 班级对象
     * @return 返回添加是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGradeClass(GradeClass gradeClass) {
        // 保存班级信息到数据库
        GradeClass dbGradeClass = gradeClassRepository.save(gradeClass);
        // 检查班级ID是否不为空以确认添加成功
        return dbGradeClass.getId() != null;
    }

    /**
     * 根据ID获取班级信息
     * @param id 班级ID
     * @return 返回班级对象
     */
    @Override
    public GradeClass getById(Long id) {
        // 根据ID查找班级，如果未找到则返回一个新的班级对象
        return gradeClassRepository.findById(id).orElseGet(GradeClass::new);
    }

    /**
     * 更新班级信息
     * @param gradeClass 班级对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editGradeClass(GradeClass gradeClass) {
        // 获取数据库中对应ID的班级对象
        GradeClass dbGradeClass = gradeClassRepository.getReferenceById(gradeClass.getId());
        // 复制属性，忽略空值和错误
        BeanUtil.copyProperties(gradeClass, dbGradeClass, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 保存更新后的班级信息
        gradeClassRepository.save(dbGradeClass);
    }

    /**
     * 根据id删除班级信息
     * @param id 班级ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 删除指定ID的班级信息
        gradeClassRepository.deleteById(id);
    }

    /**
     * 获取所有班级信息
     * @param gradeClassQueryCriteria 查询条件
     * @return 返回所有班级信息的列表
     */
    @Override
    public List<GradeClass> queryAll(GradeClassQueryCriteria gradeClassQueryCriteria) {
        // 查询所有班级信息
        return gradeClassRepository.findAll();
    }

    /**
     * 统计班级数量
     * @return 返回班级数量
     */
    @Override
    public long getCount() {
        // 返回班级的总数量
        return gradeClassRepository.count();
    }
}
