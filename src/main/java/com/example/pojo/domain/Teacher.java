package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：教师信息实体类
 * 该类用于表示教师的基本信息，包括教师工号、姓名、性别、手机号、QQ号以及教授的科目等属性。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "teachers")
@org.hibernate.annotations.Table(appliesTo = "teachers", comment = "教师信息表")
public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 教师的唯一标识符

    /**
     * 教师工号
     * 用于唯一标识教师的工号
     */
    @Column(name = "teach_no")
    private String teachno; // 教师工号

    /**
     * 教师姓名
     * 表示教师的姓名
     */
    @Column(name = "name")
    private String name; // 教师姓名

    /**
     * 教师性别
     * 表示教师的性别
     */
    @Column(name = "sex")
    private String sex; // 教师性别

    /**
     * 手机号
     * 表示教师的手机号码
     */
    @Column(name = "phone")
    private String phone; // 手机号

    /**
     * QQ号
     * 表示教师的QQ号码
     */
    @Column(name = "qq")
    private String qq; // QQ号

    /**
     * 教授科目
     * 通过一对一关系关联到课程实体
     */
    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course; // 教授的科目

}
