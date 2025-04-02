package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.pojo.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("home")
public class HomeController {

    private final IStudentService studentService; // 学生服务接口
    private final IGradeClassService gradeClassService; // 班级服务接口
    private final ITeacherService teacherService; // 教师服务接口
    private final ICourseService courseService; // 课程服务接口
    private final IScoresService scoresService; // 成绩服务接口

    // 构造函数，注入各个服务接口
    public HomeController(IStudentService studentService, IGradeClassService gradeClassService, ITeacherService teacherService, ICourseService courseService, IScoresService scoresService) {
        this.studentService = studentService;
        this.gradeClassService = gradeClassService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.scoresService = scoresService;
    }

    /**
     * 后台首页统计
     * @return 返回统计结果
     */
    @GetMapping
    public BaseResult getIndexTotal(){
        Map<String,Object> resultMap = new HashMap<>(); // 创建结果映射
        // 统计学生人数
        long studentNums = studentService.getCount(); // 获取学生数量
        resultMap.put("studentNums",studentNums); // 将学生数量放入结果映射

        // 统计班级数量
        long classNums = gradeClassService.getCount(); // 获取班级数量
        resultMap.put("classNums",classNums); // 将班级数量放入结果映射

        // 统计教师个数
        long teacherNums = teacherService.getCount(); // 获取教师数量
        resultMap.put("teacherNums",teacherNums); // 将教师数量放入结果映射

        // 统计课程门数
        long courseNums = courseService.getCount(); // 获取课程数量
        resultMap.put("courseNums",courseNums); // 将课程数量放入结果映射

        // 所有学科成绩对比
        HashMap<String, Object> scoresMap = scoresService.getAllSubjectScoreContrast(); // 获取所有学科成绩对比
        resultMap.put("scores",scoresMap); // 将成绩对比放入结果映射
        return BaseResult.success(resultMap); // 返回成功的结果
    }
}
