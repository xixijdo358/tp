package com.example.pojo.service;

import com.example.pojo.domain.Scores;
import com.example.pojo.service.dto.ScoresQueryCriteria;

import com.example.pojo.vo.EchartsSeriesModel;
import com.example.pojo.vo.RegisterScoresModel;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/** 
 * 功能描述：成绩管理业务接口
 * 该接口定义了与成绩相关的业务逻辑，包括获取成绩列表、登记班级学科成绩、更新成绩、删除成绩、统计班级科目成绩、班级学科成绩对比以及所有学科成绩对比等功能。
 */
public interface IScoresService {

    /**
     * 获取成绩列表
     * @param queryCriteria 查询条件，用于过滤成绩
     * @param pageable 分页信息，包含当前页和每页大小
     * @return 返回符合条件的成绩列表
     */
    Object getList(ScoresQueryCriteria queryCriteria, Pageable pageable);

    /**
     * 登记班级学科成绩
     * @param scoresModel 包含班级和学科成绩的模型
     */
    void registerScores(RegisterScoresModel scoresModel);

    /**
     * 更新成绩
     * @param scores 成绩对象，包含更新后的成绩信息
     */
    void editScores(Scores scores);

    /**
     * 删除成绩
     * @param id 成绩ID
     */
    void deleteById(Long id);

    /**
     * 统计班级科目成绩
     * @param courseId 课程ID
     * @param gradeClassId 班级ID
     * @return 返回班级科目成绩的统计信息
     */
    List<EchartsSeriesModel> getScoreCensus(Long courseId, Long gradeClassId);

    /**
     * 班级学科成绩对比
     * @param courseId 课程ID
     * @return 返回班级学科成绩对比的结果
     */
    HashMap<String, Object> getScoresContrastCensus(Long courseId);

    /**
     * 所有学科成绩对比
     * @return 返回所有学科成绩对比的结果
     */
    HashMap<String, Object> getAllSubjectScoreContrast();
}
