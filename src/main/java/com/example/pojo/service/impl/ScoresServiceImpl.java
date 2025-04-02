package com.example.pojo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.pojo.domain.Course;
import com.example.pojo.domain.GradeClass;
import com.example.pojo.domain.Scores;
import com.example.pojo.domain.Student;
import com.example.pojo.repository.ScoresRepository;
import com.example.pojo.repository.StudentRepository;
import com.example.pojo.service.IScoresService;
import com.example.pojo.service.dto.ScoresQueryCriteria;

import com.example.pojo.vo.BarEchartsSeriesModel;
import com.example.pojo.vo.EchartsSeriesModel;
import com.example.pojo.vo.RegisterScoresModel;
import com.example.utils.PageUtil;
import com.example.utils.QueryHelp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/** 
 * 功能描述：成绩管理业务接口实现类
 * 该类实现了IScoresService接口，提供了成绩管理的相关业务逻辑。
 * 包含获取成绩列表、登记成绩、更新成绩、删除成绩、统计成绩等功能。
 */
@Service
@Transactional(readOnly = true)
public class ScoresServiceImpl implements IScoresService {

    private final ScoresRepository scoresRepository; // 成绩数据访问接口
    private final StudentRepository studentRepository; // 学生数据访问接口

    // 构造函数，注入成绩和学生的仓库
    public ScoresServiceImpl(ScoresRepository scoresRepository, StudentRepository studentRepository) {
        this.scoresRepository = scoresRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 获取成绩列表数据
     * @param queryCriteria 查询条件
     * @param pageable 分页信息
     * @return 返回分页的成绩列表
     */
    @Override
    public Object getList(ScoresQueryCriteria queryCriteria, Pageable pageable) {
        // 根据查询条件和分页信息获取成绩数据
        Page<Scores> page = scoresRepository.findAll((root, query, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page); // 转换为分页格式
    }

    /**
     * 登记班级学科成绩
     * @param scoresModel 成绩登记模型
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 事务管理
    public void registerScores(RegisterScoresModel scoresModel) {
        // 根据班级ID获取该班级下的所有学生
        List<Student> studentList = studentRepository.findAllByGradeClassId(scoresModel.getGradeClassId());

        for(Student student: studentList) {
            // 根据课程ID和学生ID查询成绩信息
            Scores dbScores = scoresRepository.getCourseByCourseIdAndStudentId(scoresModel.getCourseId(), student.getId());
            if(dbScores == null) {
                // 新增记录
                dbScores = new Scores();
                dbScores.setType("未批改"); // 设置成绩状态
                dbScores.setScore(0); // 初始成绩为0
                dbScores.setRemarks("初始成绩"); // 备注
                dbScores.setStudent(student); // 关联学生
                Course tempCourse = new Course();
                tempCourse.setId(scoresModel.getCourseId()); // 设置课程ID
                dbScores.setCourse(tempCourse);
                GradeClass gradeClass = new GradeClass();
                gradeClass.setId(scoresModel.getGradeClassId()); // 设置班级ID
                dbScores.setGradeClass(gradeClass);
                scoresRepository.save(dbScores); // 保存成绩记录
            }
        }
    }

    /**
     * 更新成绩
     * @param scores 成绩对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editScores(Scores scores) {
        Scores dbScores = scoresRepository.getReferenceById(scores.getId()); // 获取数据库中的成绩记录
        dbScores.setType("已批改"); // 更新成绩状态
        BeanUtil.copyProperties(scores, dbScores, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true)); // 复制属性
        scoresRepository.save(dbScores); // 保存更新后的成绩记录
    }

    /**
     * 删除成绩信息
     * @param id 成绩ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        scoresRepository.deleteById(id); // 根据ID删除成绩记录
    }

    /**
     * 统计班级科目成绩
     * @param courseId 课程ID
     * @param gradeClassId 班级ID
     * @return 返回成绩统计数据
     */
    @Override
    public List<EchartsSeriesModel> getScoreCensus(Long courseId, Long gradeClassId) {
        List<EchartsSeriesModel> data = new ArrayList<>();
        // 根据班级ID和课程ID获取所有成绩信息
        List<Scores> scoresList = scoresRepository.findAllByCourseIdAndGradeClassId(courseId, gradeClassId);

        // 初始化统计变量
        AtomicInteger excellentNums = new AtomicInteger();
        EchartsSeriesModel excellentEchartsSeriesModel = new EchartsSeriesModel();
        AtomicInteger goodNums = new AtomicInteger();
        EchartsSeriesModel goodEchartsSeriesModel = new EchartsSeriesModel();
        AtomicInteger commonNums = new AtomicInteger();
        EchartsSeriesModel commonEchartsSeriesModel = new EchartsSeriesModel();
        AtomicInteger badNums = new AtomicInteger();
        EchartsSeriesModel badEchartsSeriesModel = new EchartsSeriesModel();
        AtomicInteger failNums = new AtomicInteger();
        EchartsSeriesModel failEchartsSeriesModel = new EchartsSeriesModel();

        // 遍历成绩列表进行统计
        scoresList.stream().forEach(item -> {
            if(item.getScore() >= 90) {
                excellentNums.getAndIncrement();
                excellentEchartsSeriesModel.setName("优");
                excellentEchartsSeriesModel.setValue(excellentNums.intValue());
            } else if(item.getScore() >= 80 && item.getScore() < 90) {
                goodNums.getAndIncrement();
                goodEchartsSeriesModel.setName("良");
                goodEchartsSeriesModel.setValue(goodNums.intValue());
            } else if(item.getScore() >= 70 && item.getScore() < 80) {
                commonNums.getAndIncrement();
                commonEchartsSeriesModel.setName("一般");
                commonEchartsSeriesModel.setValue(commonNums.intValue());
            } else if(item.getScore() >= 60 && item.getScore() < 70) {
                badNums.getAndIncrement();
                badEchartsSeriesModel.setName("较差");
                badEchartsSeriesModel.setValue(badNums.intValue());
            } else {
                failNums.getAndIncrement();
                failEchartsSeriesModel.setName("不及格");
                failEchartsSeriesModel.setValue(failNums.intValue());
            }
        });

        // 添加统计结果到返回数据中
        if(excellentNums.intValue() != 0) {
            data.add(excellentEchartsSeriesModel);
        }
        if(goodNums.intValue() != 0) {
            data.add(goodEchartsSeriesModel);
        }
        if(commonNums.intValue() != 0) {
            data.add(commonEchartsSeriesModel);
        }
        if(badNums.intValue() != 0) {
            data.add(badEchartsSeriesModel);
        }
        if(failNums.intValue() != 0) {
            data.add(failEchartsSeriesModel);
        }

        return data; // 返回统计结果
    }

    /**
     * 班级学科成绩对比
     * @param courseId 课程ID
     * @return 返回班级成绩对比数据
     */
    @Override
    public HashMap<String, Object> getScoresContrastCensus(Long courseId) {
        List<BarEchartsSeriesModel> barEchartsSeriesList = new ArrayList<>();
        // 获取该学科下的所有成绩记录
        List<Scores> scoresList = scoresRepository.findAllByCourseId(courseId);
        
        // 统计方法同时统计同组的最大值、最小值、计数、求和、平均数信息
        HashMap<GradeClass, DoubleSummaryStatistics> resultGradeClass = scoresList.stream()
                .collect(Collectors.groupingBy(Scores::getGradeClass, HashMap::new, Collectors.summarizingDouble(Scores::getScore)));
        
        // 初始化统计数据列表
        List<Double> averageList = new ArrayList<>();
        List<Double> maxList = new ArrayList<>();
        List<Double> minList = new ArrayList<>();
        List<Double> countList = new ArrayList<>();
        List<Double> sumList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();

        // 遍历统计结果
        resultGradeClass.forEach((k, v) -> {
            categoryList.add(k.getName()); // 班级名称
            BigDecimal bigDecimal = new BigDecimal(v.getAverage());
            double average = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue(); // 平均成绩保留两位小数
            averageList.add(average);
            maxList.add(v.getMax()); // 最高成绩
            minList.add(v.getMin()); // 最低成绩
            countList.add((double)v.getCount()); // 班级总人数
            sumList.add(v.getSum()); // 班级学科总成绩
        });

        // 创建并添加各类统计数据模型
        BarEchartsSeriesModel averageBarEchartsSeriesModel = new BarEchartsSeriesModel();
        averageBarEchartsSeriesModel.setData(averageList);
        averageBarEchartsSeriesModel.setType("bar");
        averageBarEchartsSeriesModel.setName("平均成绩");
        barEchartsSeriesList.add(averageBarEchartsSeriesModel);

        BarEchartsSeriesModel maxBarEchartsSeriesModel = new BarEchartsSeriesModel();
        maxBarEchartsSeriesModel.setData(maxList);
        maxBarEchartsSeriesModel.setType("bar");
        maxBarEchartsSeriesModel.setName("最高成绩");
        barEchartsSeriesList.add(maxBarEchartsSeriesModel);

        BarEchartsSeriesModel minBarEchartsSeriesModel = new BarEchartsSeriesModel();
        minBarEchartsSeriesModel.setData(minList);
        minBarEchartsSeriesModel.setType("bar");
        minBarEchartsSeriesModel.setName("最低成绩");
        barEchartsSeriesList.add(minBarEchartsSeriesModel);

        BarEchartsSeriesModel countBarEchartsSeriesModel = new BarEchartsSeriesModel();
        countBarEchartsSeriesModel.setData(countList);
        countBarEchartsSeriesModel.setType("bar");
        countBarEchartsSeriesModel.setName("总人数");
        barEchartsSeriesList.add(countBarEchartsSeriesModel);

        BarEchartsSeriesModel sumBarEchartsSeriesModel = new BarEchartsSeriesModel();
        sumBarEchartsSeriesModel.setData(sumList);
        sumBarEchartsSeriesModel.setType("bar");
        sumBarEchartsSeriesModel.setName("总成绩");
        barEchartsSeriesList.add(sumBarEchartsSeriesModel);

        // 定义返回对象
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("categoryList", categoryList);
        resultMap.put("barEchartsSeriesList", barEchartsSeriesList);
        return resultMap; // 返回成绩对比数据
    }

    /**
     * 所有学科成绩对比
     * @return 返回所有学科成绩对比数据
     */
    @Override
    public HashMap<String, Object> getAllSubjectScoreContrast() {
        List<BarEchartsSeriesModel> barEchartsSeriesList = new ArrayList<>();
        // 获取所有成绩记录
        List<Scores> scoresList = scoresRepository.findAll();

        // 统计方法同时统计同组的最大值、最小值、计数、求和、平均数信息
        HashMap<Course, DoubleSummaryStatistics> resultGradeClass = scoresList.stream()
                .collect(Collectors.groupingBy(Scores::getCourse, HashMap::new, Collectors.summarizingDouble(Scores::getScore)));
        
        // 初始化统计数据列表
        List<Double> averageList = new ArrayList<>();
        List<Double> maxList = new ArrayList<>();
        List<Double> minList = new ArrayList<>();
        List<Double> countList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();

        // 遍历统计结果
        resultGradeClass.forEach((k, v) -> {
            categoryList.add(k.getCoursename()); // 学科名称
            BigDecimal bigDecimal = new BigDecimal(v.getAverage());
            double average = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue(); // 平均成绩保留两位小数
            averageList.add(average);
            maxList.add(v.getMax()); // 最高成绩
            minList.add(v.getMin()); // 最低成绩
            countList.add((double)v.getCount()); // 总人数
        });

        // 创建并添加各类统计数据模型
        BarEchartsSeriesModel averageBarEchartsSeriesModel = new BarEchartsSeriesModel();
        averageBarEchartsSeriesModel.setData(averageList);
        averageBarEchartsSeriesModel.setType("bar");
        averageBarEchartsSeriesModel.setName("平均成绩");
        barEchartsSeriesList.add(averageBarEchartsSeriesModel);

        BarEchartsSeriesModel maxBarEchartsSeriesModel = new BarEchartsSeriesModel();
        maxBarEchartsSeriesModel.setData(maxList);
        maxBarEchartsSeriesModel.setType("bar");
        maxBarEchartsSeriesModel.setName("最高成绩");
        barEchartsSeriesList.add(maxBarEchartsSeriesModel);

        BarEchartsSeriesModel minBarEchartsSeriesModel = new BarEchartsSeriesModel();
        minBarEchartsSeriesModel.setData(minList);
        minBarEchartsSeriesModel.setType("bar");
        minBarEchartsSeriesModel.setName("最低成绩");
        barEchartsSeriesList.add(minBarEchartsSeriesModel);

        BarEchartsSeriesModel countBarEchartsSeriesModel = new BarEchartsSeriesModel();
        countBarEchartsSeriesModel.setData(countList);
        countBarEchartsSeriesModel.setType("bar");
        countBarEchartsSeriesModel.setName("总人数");
        barEchartsSeriesList.add(countBarEchartsSeriesModel);

        // 定义返回对象
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("categoryList", categoryList);
        resultMap.put("barEchartsSeriesList", barEchartsSeriesList);
        return resultMap; // 返回所有学科成绩对比数据
    }
}
