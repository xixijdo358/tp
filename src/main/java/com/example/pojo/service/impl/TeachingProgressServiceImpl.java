package com.example.pojo.service.impl;

import com.example.pojo.domain.TeachingPlan;
import com.example.pojo.domain.TeachingProgress;
import com.example.pojo.domain.TeachingProgressDifference;
import com.example.pojo.repository.TeachingPlanRepository;
import com.example.pojo.repository.TeachingProgressDifferenceRepository;
import com.example.pojo.repository.TeachingProgressRepository;
import com.example.pojo.service.ITeachingProgressService;
import com.example.pojo.service.dto.TeachingProgressQueryCriteria;
import com.example.utils.QueryHelp;
import com.example.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述：教学进度服务实现类
 */
@Service
public class TeachingProgressServiceImpl implements ITeachingProgressService {
    
    @Autowired
    private TeachingProgressRepository teachingProgressRepository;
    
    @Autowired
    private TeachingPlanRepository teachingPlanRepository;
    
    @Autowired
    private TeachingProgressDifferenceRepository differenceRepository;
    
    @Autowired
    private MailService mailService;
    
    @Value("${spring.mail.username:system@example.com}")
    private String systemEmail;


    public TeachingProgressServiceImpl(TeachingProgressRepository teachingProgressRepository,
                                     TeachingPlanRepository teachingPlanRepository,
                                     TeachingProgressDifferenceRepository differenceRepository) {
        this.teachingProgressRepository = teachingProgressRepository;
        this.teachingPlanRepository = teachingPlanRepository;
        this.differenceRepository = differenceRepository;
                                     }
    
    /**
     * 获取教学进度列表（分页）
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Override
    public Map<String, Object> getList(TeachingProgressQueryCriteria criteria, Pageable pageable) {
        Page<TeachingProgress> page = teachingProgressRepository.findAll((root, query, cb) -> 
                QueryHelp.getPredicate(root, criteria, cb), pageable);
        
        List<TeachingProgress> content = page.getContent();
        
        // 构建返回结果
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("content", content);
        result.put("totalElements", page.getTotalElements());
        
        return result;
    }
    
    /**
     * 根据ID获取教学进度详情
     * @param id 教学进度ID
     * @return 教学进度详情
     */
    @Override
    public TeachingProgress findById(Long id) {
        return teachingProgressRepository.findById(id).orElse(null);
    }
    
    /**
     * 创建教学进度记录
     * @param teachingProgress 教学进度对象
     * @return 创建后的教学进度对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeachingProgress create(TeachingProgress teachingProgress) {
        // 获取教学计划
        TeachingPlan plan = teachingPlanRepository.findById(teachingProgress.getTeachingPlanId())
                .orElseThrow(() -> new RuntimeException("教学计划不存在"));
        teachingProgress.setTeachingPlan(plan);
        
        // 计算预期进度
        BigDecimal expectedProgress = calculateExpectedProgress(plan.getId(), teachingProgress.getRecordDate());
        teachingProgress.setExpectedProgress(expectedProgress);
        
        // 计算实际进度百分比
        if (plan.getTotalLessons() > 0) {
            BigDecimal progressPercentage = new BigDecimal(teachingProgress.getLessonsCompleted())
                    .multiply(new BigDecimal(100))
                    .divide(new BigDecimal(plan.getTotalLessons()), 2, RoundingMode.HALF_UP);
            teachingProgress.setProgressPercentage(progressPercentage);
        } else {
            teachingProgress.setProgressPercentage(BigDecimal.ZERO);
        }
        
        // 保存进度记录
        TeachingProgress saved = teachingProgressRepository.save(teachingProgress);
        
        // 生成差异记录
        generateProgressDifference(saved);
        
        return saved;
    }
    
    /**
     * 更新教学进度记录
     * @param teachingProgress 教学进度对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TeachingProgress teachingProgress) {
        TeachingProgress existing = teachingProgressRepository.findById(teachingProgress.getId())
                .orElseThrow(() -> new RuntimeException("教学进度记录不存在"));
        
        // 获取教学计划
        TeachingPlan plan = teachingPlanRepository.findById(teachingProgress.getTeachingPlanId())
                .orElseThrow(() -> new RuntimeException("教学计划不存在"));
        
        existing.setTeachingPlan(plan);
        existing.setCompletedContent(teachingProgress.getCompletedContent());
        existing.setLessonsCompleted(teachingProgress.getLessonsCompleted());
        existing.setRecordDate(teachingProgress.getRecordDate());
        
        // 重新计算预期进度
        BigDecimal expectedProgress = calculateExpectedProgress(plan.getId(), teachingProgress.getRecordDate());
        existing.setExpectedProgress(expectedProgress);
        
        // 重新计算实际进度百分比
        if (plan.getTotalLessons() > 0) {
            BigDecimal progressPercentage = new BigDecimal(teachingProgress.getLessonsCompleted())
                    .multiply(new BigDecimal(100))
                    .divide(new BigDecimal(plan.getTotalLessons()), 2, RoundingMode.HALF_UP);
            existing.setProgressPercentage(progressPercentage);
        } else {
            existing.setProgressPercentage(BigDecimal.ZERO);
        }
        
        // 保存更新
        TeachingProgress updated = teachingProgressRepository.save(existing);
        
        // 先删除所有与该进度相关的旧差异记录
        List<TeachingProgressDifference> oldDifferences = differenceRepository.findByTeachingProgressId(updated.getId());
        if (oldDifferences != null && !oldDifferences.isEmpty()) {
            differenceRepository.deleteAll(oldDifferences);
        }
        
        // 重新生成新的差异记录
        generateProgressDifference(updated);
    }
    
    /**
     * 删除教学进度记录
     * @param id 教学进度ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 先删除相关的差异记录列表
        List<TeachingProgressDifference> differences = differenceRepository.findByTeachingProgressId(id);
        if (differences != null && !differences.isEmpty()) {
            differenceRepository.deleteAll(differences);
        }
        
        // 删除进度记录
        teachingProgressRepository.deleteById(id);
    }
    
    /**
     * 根据教学计划ID获取进度趋势数据
     * @param planId 教学计划ID
     * @return 进度趋势数据
     */
    @Override
    public List<Map<String, Object>> getPlanProgressTrend(Long planId) {
        // 获取教学计划
        TeachingPlan plan = teachingPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("教学计划不存在"));
        
        // 获取该计划的所有进度记录，按日期排序
        List<TeachingProgress> progressList = teachingProgressRepository.findByTeachingPlanIdOrderByRecordDate(planId);
        
        return progressList.stream().map(progress -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", progress.getId());
            item.put("recordDate", progress.getRecordDate().toString());
            item.put("progressPercentage", progress.getProgressPercentage());
            item.put("expectedProgress", progress.getExpectedProgress());
            item.put("lessonsCompleted", progress.getLessonsCompleted());
            item.put("completedContent", progress.getCompletedContent());
            return item;
        }).collect(Collectors.toList());
    }
    
    /**
     * 计算指定日期的预期进度
     * @param planId 教学计划ID
     * @param date 指定日期
     * @return 预期进度百分比
     */
    @Override
    public BigDecimal calculateExpectedProgress(Long planId, LocalDate date) {
        return teachingProgressRepository.calculateExpectedProgress(planId, date);
    }

    /**
     * 导出教学进度数据
     * @param queryAll 是否导出所有数据
     * @param criteria 查询条件
     * @return 导出数据列表
     */
    @Override
    public List<Map<String, Object>> exportData(Boolean queryAll, TeachingProgressQueryCriteria criteria) throws IOException {
        List<TeachingProgress> progressList;

        // 根据条件获取数据
        if (queryAll) {
            progressList = teachingProgressRepository.findAll((root, query, cb) ->
                    QueryHelp.getPredicate(root, criteria, cb));
        } else {
            progressList = new ArrayList<>();
        }

        // 构建导出数据
        return progressList.stream().map(progress -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("教学计划", progress.getTeachingPlan().getTitle());
            map.put("教师", progress.getTeachingPlan().getTeacher().getName());
            map.put("课程", progress.getTeachingPlan().getCourse().getCoursename());
            map.put("班级", progress.getTeachingPlan().getClassInfo().getName());
            map.put("记录日期", progress.getRecordDate().toString());
            map.put("完成课时", progress.getLessonsCompleted());
            map.put("总课时", progress.getTeachingPlan().getTotalLessons());
            map.put("实际进度", progress.getProgressPercentage() + "%");
            map.put("预期进度", progress.getExpectedProgress() + "%");

            // 计算差异
            BigDecimal difference = progress.getProgressPercentage().subtract(progress.getExpectedProgress());
            String status;
            if (difference.compareTo(new BigDecimal("5")) > 0) {
                status = "进度超前";
            } else if (difference.compareTo(new BigDecimal("-5")) < 0) {
                status = "进度滞后";
            } else {
                status = "正常";
            }

            map.put("差异", difference + "%");
            map.put("状态", status);
            map.put("完成内容", progress.getCompletedContent());

            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 生成教学进度差异记录
     * @param progress 教学进度对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateProgressDifference(TeachingProgress progress) {
        // 计算差异
        BigDecimal actualProgress = progress.getProgressPercentage();
        BigDecimal expectedProgress = progress.getExpectedProgress();
        BigDecimal differencePercentage = actualProgress.subtract(expectedProgress);
        
        // 根据教学计划的总课时计算差异课时数
        int totalLessons = progress.getTeachingPlan().getTotalLessons();
        int differenceLessons = differencePercentage.multiply(new BigDecimal(totalLessons))
                .divide(new BigDecimal(100), 0, RoundingMode.HALF_UP).intValue();
        
        // 创建差异记录
        TeachingProgressDifference difference = new TeachingProgressDifference();
        difference.setTeachingPlan(progress.getTeachingPlan());
        difference.setTeachingProgress(progress);
        difference.setActualProgress(actualProgress);
        difference.setExpectedProgress(expectedProgress);
        difference.setDifference(differenceLessons);
        difference.setDifferencePercentage(differencePercentage);
        
        // 设置预警状态
        boolean isWarning = false;
        int warningLevel = 0;
        String warningMessage = "";
        
        if (differencePercentage.compareTo(new BigDecimal("-10")) <= 0) {
            isWarning = true;
            warningLevel = 2; // 严重滞后
            warningMessage = "教学进度严重滞后，请及时调整教学安排";
        } else if (differencePercentage.compareTo(new BigDecimal("-5")) <= 0) {
            isWarning = true;
            warningLevel = 1; // 轻微滞后
            warningMessage = "教学进度轻微滞后，建议关注教学进展";
        }
        
        difference.setWarning(isWarning);
        difference.setWarningLevel(warningLevel);
        difference.setWarningMessage(warningMessage);
        difference.setStatus(1); // 1-正常，0-已处理
        
        // 保存差异记录
        TeachingProgressDifference saved = differenceRepository.save(difference);
        
        // 如果是预警记录，发送邮件通知
        if (saved.getWarning()) {
            sendWarningNotification(saved);
        }
    }
    
    /**
     * 发送预警通知邮件
     * @param difference 教学进度差异记录
     */
    private void sendWarningNotification(TeachingProgressDifference difference) {
        TeachingPlan plan = difference.getTeachingPlan();
        
        // 如果教师有手机号，发送邮件通知（实际项目中可能需要添加email字段，或使用其他通知方式）
        if (plan != null && plan.getTeacher() != null && plan.getTeacher().getPhone() != null) {
            // 这里假设用手机号作为邮箱前缀，实际应用中应该添加email字段
            String teacherEmail = plan.getTeacher().getPhone() + "@example.com"; 
            String teacherName = plan.getTeacher().getName();
            String subject = "教学进度预警通知";
            
            // 构建邮件内容
            StringBuilder content = new StringBuilder();
            content.append("尊敬的").append(teacherName).append("老师：\n\n");
            content.append("您的教学计划《").append(plan.getTitle()).append("》有新的预警信息：\n\n");
            content.append("预警级别：").append(difference.getWarningLevel() == 2 ? "严重" : "轻微").append("\n");
            content.append("预警消息：").append(difference.getWarningMessage()).append("\n");
            content.append("实际进度：").append(difference.getActualProgress()).append("%\n");
            content.append("预期进度：").append(difference.getExpectedProgress()).append("%\n");
            content.append("差异：").append(difference.getDifferencePercentage()).append("%\n\n");
            content.append("请及时登录系统查看详情并处理。\n\n");
            content.append("此邮件由系统自动发送，请勿回复。");
            
            try {
                mailService.sendSimpleMail(
                    systemEmail,    // 系统邮箱
                    teacherEmail,   // 收件人
                    null,           // 抄送
                    subject,        // 主题
                    content.toString() // 内容
                );
            } catch (Exception e) {
                // 邮件发送失败，记录日志但不影响业务流程
                System.err.println("预警邮件发送失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 获取预警列表
     * @param warningType 预警类型（基于差异百分比的描述，如"进度滞后"）
     * @param status 处理状态（1-未处理，0-已处理）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param pageable 分页参数
     * @return 预警列表数据
     */
    @Override
    public Map<String, Object> getWarningList(String warningType, Integer status, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        // 构建查询条件
        Specification<TeachingProgressDifference> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 只查询预警状态为true的记录
            predicates.add(cb.equal(root.get("warning"), true));
            
            // 添加状态筛选条件
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            
            // 添加预警类型筛选条件
            if (warningType != null && !warningType.isEmpty()) {
                if ("进度滞后".equals(warningType)) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("differencePercentage"), new BigDecimal("-10")));
                } else if ("轻微滞后".equals(warningType)) {
                    predicates.add(cb.between(root.get("differencePercentage"), new BigDecimal("-10"), new BigDecimal("-5")));
                }
            }
            
            // 添加日期范围筛选条件 (createTime 是 LocalDateTime)
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), startDate.atStartOfDay()));
            }
            if (endDate != null) {
                predicates.add(cb.lessThan(root.get("createTime"), endDate.plusDays(1).atStartOfDay()));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        // 查询数据
        Page<TeachingProgressDifference> page = differenceRepository.findAll(spec, pageable);
        
        // 转换为前端需要的格式
        List<Map<String, Object>> content = page.getContent().stream().map(difference -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", difference.getId());
            item.put("planTitle", difference.getTeachingPlan().getTitle());
            
            // 设置预警类型
            String warnType = "未知";
            if (difference.getDifferencePercentage().compareTo(new BigDecimal("-10")) <= 0) {
                warnType = "进度滞后";
            } else if (difference.getDifferencePercentage().compareTo(new BigDecimal("-5")) <= 0) {
                warnType = "轻微滞后";
            }
            item.put("warningType", warnType);
            
            // 设置日期和内容
            item.put("warningDate", difference.getCreateTime());
            item.put("content", difference.getWarningMessage());
            
            // 设置状态
            String statusText = difference.getStatus() == 1 ? "未处理" : "已处理";
            item.put("status", statusText);
            
            // 添加详情数据
            item.put("recordDate", difference.getTeachingProgress().getRecordDate());
            item.put("warningLevel", difference.getWarningLevel());
            item.put("actualProgress", difference.getActualProgress());
            item.put("expectedProgress", difference.getExpectedProgress());
            item.put("difference", difference.getDifference());
            item.put("differencePercentage", difference.getDifferencePercentage());
            
            return item;
        }).collect(Collectors.toList());
        
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("totalElements", page.getTotalElements());
        
        return result;
    }
    
    /**
     * 处理预警
     * @param id 预警记录ID
     * @param processMethod 处理方式
     * @param processRemark 处理说明
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processWarning(Long id, String processMethod, String processRemark) {
        // 获取预警记录
        TeachingProgressDifference difference = differenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预警记录不存在"));
        
        // 更新处理状态
        difference.setStatus(0); // 标记为已处理
        
        // 添加处理信息（这些字段在原实体类中可能不存在，需要添加）
        // 如果字段不存在，可以考虑在备注字段中记录处理信息
        if (difference.getRemarks() == null) {
            difference.setRemarks("处理方式: " + processMethod + ", 处理说明: " + processRemark);
        } else {
            difference.setRemarks(difference.getRemarks() + "\n处理方式: " + processMethod + ", 处理说明: " + processRemark);
        }
        
        // 更新修改时间由 @UpdateTimestamp 自动处理，不需要手动设置
        
        // 保存更新
        differenceRepository.save(difference);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWarningsBatch(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            differenceRepository.deleteAllByIdInBatch(ids); // 或 deleteAllById(ids)
            // 或者更安全的方式，逐个查找并删除
            // List<TeachingProgressDifference> differencesToDelete = differenceRepository.findAllById(ids);
            // if (!differencesToDelete.isEmpty()) {
            //     differenceRepository.deleteAllInBatch(differencesToDelete);
            // }
        }
    }
} 