package com.example.pojo.service;

import com.example.pojo.domain.GradeClass;
import com.example.pojo.service.dto.GradeClassQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/** 
 * 功能描述：班级信息业务接口
 * 该接口定义了与班级相关的业务逻辑，包括获取班级列表、添加班级、获取班级详情、更新班级信息、删除班级以及统计班级数量等功能。
 */
public interface IGradeClassService {

    /**
     * 获取班级列表数据
     * @param queryCriteria 查询条件，用于过滤班级
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的班级列表
     */
    Object getList(GradeClassQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 添加班级信息
     * @param gradeClass 班级对象，包含班级的详细信息
     * @return 返回添加是否成功
     */
    boolean addGradeClass(GradeClass gradeClass);

    /**
     * 根据ID获取班级信息
     * @param id 班级ID
     * @return 返回对应的班级对象
     */
    GradeClass getById(Long id);

    /**
     * 更新班级信息
     * @param gradeClass 班级对象，包含更新后的班级信息
     */
    void editGradeClass(GradeClass gradeClass);

    /**
     * 根据ID删除班级信息
     * @param id 班级ID
     */
    void deleteById(Long id);

    /**
     * 获取所有班级信息
     * @param gradeClassQueryCriteria 查询条件，用于过滤班级
     * @return 返回所有符合条件的班级列表
     */
    List<GradeClass> queryAll(GradeClassQueryCriteria gradeClassQueryCriteria);

    /**
     * 统计班级数量
     * @return 返回班级的总数量
     */
    long getCount();
}
