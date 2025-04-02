package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：学生成绩实体类
 * 该类用于表示学生的成绩信息，包括学生、课程和班级等相关信息。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "student_score")
@org.hibernate.annotations.Table(appliesTo = "student_score", comment = "学生成绩表")
public class Scores extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 成绩的唯一标识符

    /**
     * 所属班级
     * 通过一对一关系关联到班级实体
     */
    @OneToOne
    @JoinColumn(name = "gradeclass_id", referencedColumnName = "id")
    private GradeClass gradeClass; // 班级对象

    /**
     * 学生对象
     * 通过一对一关系关联到学生实体
     */
    @OneToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student; // 学生对象

    /**
     * 课程对象
     * 通过一对一关系关联到课程实体
     */
    @OneToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course; // 课程对象

    /**
     * 学生成绩
     * 表示学生在该课程中的成绩
     */
    @Column(name = "score")
    private float score; // 学生成绩

    /**
     * 是否批改：未批改、已批改
     * 表示该成绩是否已经被教师批改
     */
    @Column(name = "type")
    public String type; // 成绩状态

}
