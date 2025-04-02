package com.example.pojo.domain;

import com.example.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：学生信息实体类
 * 该类用于表示学生的基本信息，包括学号、姓名、所属班级、性别、手机号和QQ等属性。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "students")
@org.hibernate.annotations.Table(appliesTo = "students", comment = "学生信息表")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 学号
     */
    @Column(name = "stuno")
    private String stuno;

    /**
     * 学生姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 所属班级
     */
    @JoinColumn(name = "grade_class_id", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private GradeClass gradeClass;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 手机号
     */
    @Column(name="phone")
    private String phone;

    /**
     * QQ
     */
    @Column(name = "qq")
    private String qq;




}
