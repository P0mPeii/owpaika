package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Orders;
import com.owpai.pojo.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Orders add(OrderDTO orderDTO);

    void delete(Long id);

    Orders select(Long id);

    void update(OrderStatus status, Long id);

    Page<Orders> pageQuery(Integer page, Integer pageSize);

    List<Orders> selectEmail(String email);

    Orders selectNumber(String orderNum);

    /**
     * 取消订单
     * 
     * @param orderNum 订单号
     */
    void cancelOrder(String orderNum);

    /**
     * 获取导出订单的数据
     * 
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param status    订单状态
     * @return 符合条件的订单列表
     */
    List<Orders> getOrdersForExport(LocalDateTime startTime, LocalDateTime endTime, OrderStatus status);
}
