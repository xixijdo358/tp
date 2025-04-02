package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：系统用户实体类
 * 该类用于表示系统中的用户信息，包括用户的用户名、密码、真实姓名、性别、状态、电子邮件、用户头像和所属角色等属性。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "users")
@org.hibernate.annotations.Table(appliesTo = "users",comment="系统用户信息表")
public class SysUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 用户的唯一标识符

    @Column(name = "password")
    private String password; // 用户的密码

    @Column(name = "username")
    private String username; // 用户名

    @Column(name="realname")
    private String realname; // 用户的真实姓名

    @Column(name="sex")
    private String sex; // 用户的性别

    @Column(name = "status")
    private Integer status; // 用户的状态（如激活、禁用等）

    @Column(name = "email")
    private String email; // 用户的电子邮件地址

    @Column(name = "user_icon")
    private String userIcon; // 用户头像的URL

    /**
     * 所属角色
     * 该字段表示用户所拥有的角色，通过一对一关系关联到SysRole实体
     */
    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName="id")
    private SysRole sysRole; // 用户的角色信息
}
