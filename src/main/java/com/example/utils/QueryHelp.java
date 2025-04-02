package com.example.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.annotation.EnableXuedenQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**功能描述：查询工具类
 * 该类提供了根据查询对象和CriteriaBuilder构建查询条件的方法。
 */
@Slf4j
public class QueryHelp {
    /**
     * 根据给定的查询对象和CriteriaBuilder构建查询条件
     * @param root 查询的根对象
     * @param query 查询条件对象
     * @param cb CriteriaBuilder对象
     * @return 返回构建的Predicate对象
     */
    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        log.debug("开始构建查询条件，查询类: {}", query != null ? query.getClass().getSimpleName() : "null");

        // 如果查询对象为空，返回一个空的and条件
        if(query == null){
            log.debug("查询对象为空，返回空条件");
            return cb.and(list.toArray(new Predicate[0]));
        }
        try {
            // 获取查询对象的所有字段
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<Field>());
            log.debug("查询对象字段数量: {}", fields.size());
            
            for (Field field : fields) {
                // 测试调用者是否可以访问此反射对象
                boolean accessible = field.canAccess(query);
                // 设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                EnableXuedenQuery q = field.getAnnotation(EnableXuedenQuery.class);
                if (q != null) {
                    String propName = q.propName(); // 获取属性名称
                    String joinName = q.joinName(); // 获取连接名称
                    String blurry = q.blurry(); // 获取模糊查询字段
                    String attributeName = isBlank(propName) ? field.getName() : propName; // 确定属性名称
                    Class<?> fieldType = field.getType(); // 获取字段类型
                    Object val = field.get(query); // 获取字段值
                    
                    log.debug("处理查询字段: {}, 属性名: {}, 连接名: {}, 模糊查询: {}, 值: {}",
                            field.getName(), attributeName, joinName, blurry, val);
                    
                    // 如果字段值为空，则跳过
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        log.debug("字段 {} 的值为空，跳过", field.getName());
                        continue;
                    }
                    Join join = null; // 初始化连接对象
                    // 处理模糊多字段查询
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        List<Predicate> orPredicate = new ArrayList<Predicate>();
                        for (String s : blurrys) {
                            orPredicate.add(cb.like(root.get(s)
                                    .as(String.class), "%" + val.toString() + "%")); // 添加模糊查询条件
                        }
                        Predicate[] p = new Predicate[orPredicate.size()];
                        list.add(cb.or(orPredicate.toArray(p))); // 添加or条件
                        continue;
                    }
                    // 处理连接查询
                    if (ObjectUtil.isNotEmpty(joinName)) {
                        try {
                            // 支持使用 "." 或 ">" 作为分隔符
                            String[] joinNames;
                            if (joinName.contains(">")) {
                                joinNames = joinName.split(">");
                                log.debug("使用 > 分隔符解析关联路径: {}", Arrays.toString(joinNames));
                            } else if (joinName.contains(".")) {
                                joinNames = joinName.split("\\.");
                                log.debug("使用 . 分隔符解析关联路径: {}", Arrays.toString(joinNames));
                            } else {
                                joinNames = new String[]{joinName};
                                log.debug("使用单一关联路径: {}", joinName);
                            }
                            
                            for (String name : joinNames) {
                                switch (q.join()) {
                                    case LEFT:
                                        if(ObjectUtil.isNotNull(join)){
                                            log.debug("创建左连接: {} 在已有连接上", name);
                                            join = join.join(name, JoinType.LEFT); // 左连接
                                        } else {
                                            log.debug("创建左连接: {} 在根上", name);
                                            join = root.join(name, JoinType.LEFT); // 左连接
                                        }
                                        break;
                                    case RIGHT:
                                        if(ObjectUtil.isNotNull(join)){
                                            log.debug("创建右连接: {} 在已有连接上", name);
                                            join = join.join(name, JoinType.RIGHT); // 右连接
                                        } else {
                                            log.debug("创建右连接: {} 在根上", name);
                                            join = root.join(name, JoinType.RIGHT); // 右连接
                                        }
                                        break;
                                    default: break;
                                }
                            }
                        } catch (Exception e) {
                            log.error("处理关联查询路径时出错: " + joinName, e);
                        }
                    }
                    // 根据查询类型构建不同的查询条件
                    switch (q.type()) {
                        case EQUAL:
                            list.add(cb.equal(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType),val)); // 等于条件
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val)); // 大于等于条件
                            break;
                        case LESS_THAN:
                            list.add(cb.lessThanOrEqualTo(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val)); // 小于等于条件
                            break;
                        case LESS_THAN_NQ:
                            list.add(cb.lessThan(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val)); // 小于条件
                            break;
                        case INNER_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString() + "%")); // 内部模糊查询
                            break;
                        case LEFT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), "%" + val.toString())); // 左模糊查询
                            break;
                        case RIGHT_LIKE:
                            list.add(cb.like(getExpression(attributeName,join,root)
                                    .as(String.class), val.toString() + "%")); // 右模糊查询
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>)val)) {
                                list.add(getExpression(attributeName,join,root).in((Collection<Long>) val)); // 在集合中查询
                            }
                            break;
                        case NOT_EQUAL:
                            list.add(cb.notEqual(getExpression(attributeName,join,root), val)); // 不等于条件
                            break;
                        case NOT_NULL:
                            list.add(cb.isNotNull(getExpression(attributeName,join,root))); // 非空条件
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<Object>((List<Object>)val);
                            list.add(cb.between(getExpression(attributeName, join, root).as((Class<? extends Comparable>) between.get(0).getClass()),
                                    (Comparable) between.get(0), (Comparable) between.get(1))); // 区间条件
                            break;
                        default: break;
                    }
                }
                field.setAccessible(accessible); // 恢复字段的访问权限
            }
        } catch (Exception e) {
            log.error("构建查询条件时出错: {}", e.getMessage(), e); // 记录异常信息
        }
        int size = list.size();
        log.debug("构建查询条件完成，条件数量: {}", size);
        return cb.and(list.toArray(new Predicate[size])); // 返回构建的and条件
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        // 根据连接对象返回相应的表达式
        try {
            if (ObjectUtil.isNotEmpty(join)) {
                log.debug("从连接获取表达式: {}.{}", join.getAttribute().getName(), attributeName);
                return join.get(attributeName);
            } else {
                log.debug("从根获取表达式: {}", attributeName);
                return root.get(attributeName);
            }
        } catch (Exception e) {
            log.error("获取表达式时出错: " + attributeName, e);
            throw e;
        }
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        // 判断字符序列是否为空或全为空格
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        // 递归获取所有字段
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
