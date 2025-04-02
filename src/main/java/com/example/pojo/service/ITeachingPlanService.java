package com.example.pojo.service;

import com.example.pojo.domain.TeachingPlan;
import com.example.pojo.service.dto.TeachingPlanQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：教学计划服务接口
 * 提供教学计划相关的业务方法
 */
public interface ITeachingPlanService {
    /**
     * 获取所有教学计划
     * @param queryCriteria 查询条件
     * @param pageable 分页参数
     * @return 返回符合条件教学计划列表
     */
    Object getList(TeachingPlanQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 根据ID获取教学计划详情
     * @param id 教学计划ID
     * @return 返回教学计划详情
     */
    TeachingPlan findById(Long id);

    /**
     * 新增教学计划
     * @param teachingPlan 教学计划对象
     * @return 返回新增的教学计划
     */
    TeachingPlan create(TeachingPlan teachingPlan);

    /**
     * 更新教学计划
     * @param teachingPlan 教学计划对象
     */
    void update(TeachingPlan teachingPlan);

    /**
     * 删除教学计划
     * @param id 教学计划ID
     */
    void delete(Long id);

    /**
     * 导出教学计划数据
     * @param queryAll 查询所有结果
     * @param queryCriteria 查询条件
     * @return 返回导出的数据列表
     */
    List<Map<String, Object>> exportData(Boolean queryAll, TeachingPlanQueryCriteria queryCriteria);
}