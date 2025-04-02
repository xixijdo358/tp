package com.example.pojo.domain;

import com.example.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**功能描述：班级信息实体类
 * 该类用于表示班级的基本信息，包括班级编号、年级、班级名称和班级类型。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "classes")
@org.hibernate.annotations.Table(appliesTo = "classes", comment = "班级信息表")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 班级的唯一标识符

    /**
     * 班级编号
     * 用于唯一标识班级的编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 年级
     * 表示班级所属的年级
     */
    @Column(name = "grade")
    private Integer grade;

    /**
     * 名称
     * 表示班级的名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 班级
     * 表示班级数字，如01，02等
     */
    @Column(name = "clazz")
    private Integer clazz;

    /**
     * 班级中的学生列表
     * 通过一对多关系映射到学生实体
     */
    @JsonIgnoreProperties(value = {"gradeClass"})
    @OneToMany(mappedBy = "gradeClass", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();
}
