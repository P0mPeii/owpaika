package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.owpai.pojo.entity.Order;
import com.owpai.pojo.vo.OrderDashboardTimeVO;
import com.owpai.pojo.vo.OrderDashboardVO;
import com.owpai.server.mapper.OrderMapper;
import com.owpai.server.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDashboardServiceImpl implements OrderDashboardService {

    private final OrderMapper orderMapper;

    @Override
    public OrderDashboardTimeVO getOrderDashboardData() {
        return OrderDashboardTimeVO.builder()
                .today(getOrderDashboardVO(getTodayStart(), LocalDateTime.now()))
                .lastWeek(getOrderDashboardVO(getLastWeekStart(), LocalDateTime.now()))
                .lastMonth(getOrderDashboardVO(getLastMonthStart(), LocalDateTime.now()))
                .lastYear(getOrderDashboardVO(getLastYearStart(), LocalDateTime.now()))
                .build();
    }


    public OrderDashboardVO getOrderDashboardVO(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .between(Order::getCreateTime, startTime, endTime);

        List<Order> orders = orderMapper.selectList(queryWrapper);

        long totalOrders = orders.size();
        BigDecimal totalAmount = orders.stream()
                .map(Order::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long pendingOrders = orders.stream()
                .filter(order -> order.getStatus() == 0)
                .count();

        long processingOrders = orders.stream()
                .filter(order -> order.getStatus() == 1)
                .count();

        long completedOrders = orders.stream()
                .filter(order -> order.getStatus() == 2)
                .count();

        long failedOrders = orders.stream()
                .filter(order -> order.getStatus() == 3)
                .count();

        long abnormalOrders = orders.stream()
                .filter(order -> order.getStatus() == 4 || order.getStatus() == 5)
                .count();

        return OrderDashboardVO.builder()
                .totalOrders(totalOrders)
                .totalAmount(totalAmount)
                .pendingOrders(pendingOrders)
                .processingOrders(processingOrders)
                .completedOrders(completedOrders)
                .failedOrders(failedOrders)
                .abnormalOrders(abnormalOrders)
                .build();
    }

    private LocalDateTime getTodayStart() {
        return LocalDateTime.now().with(LocalTime.MIN);
    }

    private LocalDateTime getLastWeekStart() {
        return LocalDateTime.now().minusWeeks(1).with(LocalTime.MIN);
    }

    private LocalDateTime getLastMonthStart() {
        return LocalDateTime.now().minusMonths(1).with(LocalTime.MIN);
    }

    private LocalDateTime getLastYearStart() {
        return LocalDateTime.now().minusYears(1).with(LocalTime.MIN);
    }
}