package com.owpai.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDashboardTimeVO implements Serializable {
    // 今日数据
    private OrderDashboardVO today;

    // 近一周数据
    private OrderDashboardVO lastWeek;

    // 近一月数据
    private OrderDashboardVO lastMonth;

    // 近一年数据
    private OrderDashboardVO lastYear;
}