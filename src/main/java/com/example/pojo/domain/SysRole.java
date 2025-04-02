package com.example.pojo.domain;

import com.example.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**功能描述：系统角色实体类
 * 该类用于表示系统中的角色信息，包括角色的名称和编号。
 * 继承自BaseEntity，包含通用的实体属性。
 */
@Data
@Entity
@Table(name = "roles")
@org.hibernate.annotations.Table(appliesTo = "roles",comment="系统角色信息表")
public class SysRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 角色的唯一标识符

    /**
     * 角色名称
     * 表示角色的名称，用于描述该角色的功能或权限
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色编号
     * 用于唯一标识角色的编号
     */
    @Column(name = "code")
    private String code;

}
