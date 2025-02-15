package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.owpai.pojo.entity.Orders;
import com.owpai.pojo.enums.OrderStatus;
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
                LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<Orders>()
                                .between(Orders::getCreateTime, startTime, endTime);

                List<Orders> orders = orderMapper.selectList(queryWrapper);

                long totalOrders = orders.size();
                BigDecimal totalAmount = orders.stream()
                                .map(Orders::getPayAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                long pendingOrders = orders.stream()
                                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                                .count();

                long processingOrders = orders.stream()
                                .filter(order -> order.getStatus() == OrderStatus.PAID)
                                .count();

                long completedOrders = orders.stream()
                                .filter(order -> order.getStatus() == OrderStatus.SENT)
                                .count();

                long failedOrders = orders.stream()
                                .filter(order -> order.getStatus() == OrderStatus.CANCEL)
                                .count();

                long abnormalOrders = orders.stream()
                                .filter(order -> List.of(OrderStatus.REFUND, OrderStatus.REFUSE_REFUND)
                                                .contains(order.getStatus()))
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