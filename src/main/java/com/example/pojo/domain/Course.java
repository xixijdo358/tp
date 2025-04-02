package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：课程信息实体类
 * 该类用于表示课程的基本信息，包括课程编号和课程名称。
 * 继承自BaseEntity，包含通用的实体属性。dD
 */
@Data
@Entity
@Table(name = "courses")
@org.hibernate.annotations.Table(appliesTo = "courses",comment="课程信息表")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 课程的唯一标识符

    /**
     * 课程编号
     * 用于唯一标识课程的编号
     */
    @Column(name = "course_no")
    private String courseno;

    /**
     * 课程名称
     * 表示课程的名称
     */
    @Column(name = "course_name")
    private String coursename;

}
