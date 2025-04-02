package com.example.utils;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**功能描述：分页工具
 * 该类提供了分页相关的工具方法，主要用于处理列表和分页数据的转换。
 */
public class PageUtil extends cn.hutool.core.util.PageUtil{

    /**
     * List 分页
     * 该方法根据给定的页码和每页大小，将列表进行分页处理。
     * @param page 当前页码，从0开始
     * @param size 每页的大小
     * @param list 需要分页的列表
     * @return 返回分页后的列表
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size; // 计算当前页的起始索引
        int toIndex = page * size + size; // 计算当前页的结束索引
        if(fromIndex > list.size()){
            return new ArrayList(); // 如果起始索引超出列表大小，返回空列表
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size()); // 返回从起始索引到列表末尾的子列表
        } else {
            return list.subList(fromIndex,toIndex); // 返回当前页的子列表
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     * 该方法将Page对象转换为Map格式，方便前端使用。
     * @param page Page对象
     * @return 返回包含内容和总元素数的Map
     */
    public static Map<String,Object> toPage(Page page) {
        Map<String,Object> map = new LinkedHashMap<>(2); // 创建一个容量为2的LinkedHashMap
        map.put("content",page.getContent()); // 将Page内容放入Map中
        map.put("totalElements",page.getTotalElements()); // 将总元素数放入Map中
        return map; // 返回Map
    }

    /**
     * 自定义分页
     * 该方法允许用户自定义内容和总元素数，返回Map格式。
     * @param object 自定义内容
     * @param totalElements 总元素数
     * @return 返回包含自定义内容和总元素数的Map
     */
    public static Map<String,Object> toPage(Object object, Object totalElements) {
        Map<String,Object> map = new LinkedHashMap<>(2); // 创建一个容量为2的LinkedHashMap
        map.put("content",object); // 将自定义内容放入Map中
        map.put("totalElements",totalElements); // 将总元素数放入Map中
        return map; // 返回Map
    }
}
