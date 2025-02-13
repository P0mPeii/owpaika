package com.owpai.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDashboardVO implements Serializable {
    // 总订单数
    private Long totalOrders;

    // 总销售额
    private BigDecimal totalAmount;

    // 待处理订单数（待付款）
    private Long pendingOrders;

    // 处理中订单数（待发货）
    private Long processingOrders;

    // 已完成订单数
    private Long completedOrders;

    // 失败订单数（已取消）
    private Long failedOrders;

    // 异常订单数（待退款、已退款）
    private Long abnormalOrders;
}