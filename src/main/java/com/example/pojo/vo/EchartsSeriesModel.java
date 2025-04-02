package com.example.pojo.vo;

import lombok.Data;

/** 
 * Echarts返回结果集对象
 * 该类用于封装Echarts图表的数据系列，包括每个数据点的值和名称。
 */
@Data
public class EchartsSeriesModel {
    // 数据点的值，通常表示该系列在图表中的数值
    private Integer value;
    // 数据点的名称，用于标识该系列数据
    private String name;
}
