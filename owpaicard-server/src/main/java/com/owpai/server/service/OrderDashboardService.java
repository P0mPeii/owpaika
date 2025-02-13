package com.owpai.server.service;

import com.owpai.pojo.vo.OrderDashboardTimeVO;
import com.owpai.pojo.vo.OrderDashboardVO;

import java.time.LocalDateTime;

public interface OrderDashboardService {
    /**
     * 获取订单仪表盘数据
     * 
     * @return 包含今日、近一周、近一月、近一年的订单统计数据
     */
    OrderDashboardTimeVO getOrderDashboardData();

    OrderDashboardVO getOrderDashboardVO(LocalDateTime startTime, LocalDateTime endTime);
}