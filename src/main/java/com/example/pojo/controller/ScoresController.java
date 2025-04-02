package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.Scores;

import com.example.pojo.service.IScoresService;
import com.example.pojo.service.dto.ScoresQueryCriteria;

import com.example.pojo.vo.EchartsSeriesModel;
import com.example.pojo.vo.RegisterScoresModel;
import com.example.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**功能描述：成绩管理前端控制器
 * 该控制器负责处理与成绩相关的HTTP请求，包括获取成绩列表、登记成绩、更新成绩、删除成绩、统计班级学科成绩和成绩对比等功能。
 */
@RestController
@RequestMapping("scores")
public class ScoresController {

    private final IScoresService scoresService; // 成绩服务接口

    public ScoresController(IScoresService scoresService) {
        this.scoresService = scoresService; // 构造函数注入成绩服务
    }

    /**
     * 获取成绩列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回成绩列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(ScoresQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回成绩列表
        return new ResponseEntity<>(scoresService.getList(queryCriteria, pageable), HttpStatus.OK);
    }

    /**
     * 登记班级学科成绩
     * @param scoresModel 成绩登记模型
     * @return 返回登记结果
     */
    @PostMapping
    public BaseResult registerScores(@RequestBody RegisterScoresModel scoresModel){
        scoresService.registerScores(scoresModel); // 调用服务登记成绩
        return BaseResult.success("登记成功"); // 返回成功信息
    }

    /**
     * 更新成绩
     * @param scores 成绩对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editScores(@RequestBody Scores scores){
        scoresService.editScores(scores); // 调用服务更新成绩
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除成绩信息
     * @param id 成绩ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if (null == id) {
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        scoresService.deleteById(id); // 调用服务删除成绩
        return BaseResult.success("删除成功"); // 返回成功信息
    }

    /**
     * 统计班级学科成绩
     * @param courseId 课程ID
     * @param gradeClassId 班级ID
     * @return 返回班级学科成绩统计结果
     */
    @GetMapping("getScoreCensus")
    public BaseResult getScoreCensus(@RequestParam("courseId") Long courseId,
                                      @RequestParam("gradeClassId") Long gradeClassId) {
        List<EchartsSeriesModel> list = scoresService.getScoreCensus(courseId, gradeClassId); // 获取成绩统计数据
        return BaseResult.success(list); // 返回成功信息和统计数据
    }

    /**
     * 班级学科成绩对比
     * @param courseId 课程ID
     * @return 返回成绩对比结果
     */
    @GetMapping("getScoresContrastCensus")
    public BaseResult getScoresContrastCensus(@RequestParam("courseId") Long courseId) {
        return BaseResult.success(scoresService.getScoresContrastCensus(courseId)); // 返回成绩对比结果
    }

}
