package com.example.pojo.vo;

import lombok.Data;

import java.util.List;

/** 
 * 柱形图返回结果集对象
 * 该类用于封装柱形图的数据结构，包含图表的数据、类型和名称。
 */
@Data
public class BarEchartsSeriesModel {
    // 存储柱形图的数据列表
    private List<Double> data;
    // 图表的类型，例如 'bar' 表示柱形图
    private String type;
    // 图表的名称，用于标识该系列数据
    private String name;
}
