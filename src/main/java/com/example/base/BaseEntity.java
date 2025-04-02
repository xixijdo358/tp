package com.example.base;

import com.example.annotation.EnableXuedenCreateBy;
import com.example.annotation.EnableXuedenUpdateBy;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**功能描述：公共Entity
 * 该类是所有实体类的基类，提供了创建时间、创建者ID、更新时间、更新者ID和备注字段。
 * 通过使用注解，可以自动填充创建者和更新者的ID。
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * 创建时间
     * 使用@CreationTimestamp注解自动填充创建时间
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    /**
     * 创建者ID
     * 使用@EnableXuedenCreateBy注解标记该字段，以便在插入数据时自动填充创建者ID
     */
    @Column(name = "create_by")
    @EnableXuedenCreateBy
    private Long createBy;

    /**
     * 更新时间
     * 使用@UpdateTimestamp注解自动填充更新时间
     */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    /**
     * 更新者ID
     * 使用@EnableXuedenUpdateBy注解标记该字段，以便在更新数据时自动填充更新者ID
     */
    @Column(name = "update_by")
    @EnableXuedenUpdateBy
    private Long updateBy;

    /**
     * 备注
     * 用于存储额外信息
     */
    @Column(name = "remarks")
    private String remarks;
    // 该注解用于标记更新操作，可能用于标识某些特定的更新行为
    public @interface Update {}

    @Override
    public String toString() {
        // 创建一个ToStringBuilder实例，用于构建对象的字符串表示
        ToStringBuilder builder = new ToStringBuilder(this);
        // 获取当前类的所有声明字段
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            // 遍历每个字段
            for (Field f : fields) {
                // 设置字段可访问，以便获取其值
                f.setAccessible(true);
                // 将字段名和对应的值添加到构建器中，并换行
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        } catch (Exception e) {
            // 如果在构建过程中发生异常，添加错误信息
            builder.append("toString builder encounter an error");
        }
        // 返回构建的字符串表示
        return builder.toString();
    }
}
